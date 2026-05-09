# 🔐 Sistema de Autenticación - Flujo Completo

## Diagrama del Flujo de Autenticación

```
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND (Angular)                       │
│                                                                   │
│  1. Usuario abre /auth/login                                     │
│     └─> LoginComponent cargado                                   │
│                                                                   │
│  2. Usuario ingresa credenciales                                 │
│     ├─ username: "joao"                                          │
│     └─ password: "password123"                                   │
│                                                                   │
│  3. LoginComponent llama a AuthService.login()                   │
└─────────────────────────────────────────────────────────────────┘
                            ↓ HTTP POST
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND (Spring Boot)                         │
│                                                                   │
│  POST /auth/log-in                                               │
│  {                                                               │
│    "username": "joao",                                           │
│    "password": "password123"                                     │
│  }                                                               │
│                                                                   │
│  ✓ Backend valida credenciales                                  │
│  ✓ Genera JWT token                                             │
│  ✓ Retorna respuesta:                                           │
│  {                                                               │
│    "username": "joao",                                           │
│    "message": "User logged",                                     │
│    "jwt": "eyJhbGciOiJIUzI1NiIsInR5...",                         │
│    "status": true                                                │
│  }                                                               │
└─────────────────────────────────────────────────────────────────┘
                            ↓ HTTP Response
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND (Angular)                       │
│                                                                   │
│  4. AuthService recibe el token                                  │
│     ├─ this.setToken(response.jwt)                              │
│     │  └─> localStorage.setItem('token', 'eyJhbGc...')          │
│     └─ this.setCurrentUser(user)                                │
│        └─> localStorage.setItem('user', user JSON)              │
│                                                                   │
│  5. Router navega a /posts                                       │
│     └─> PostsListComponent cargado                              │
│                                                                   │
│  6. Usuario autenticado ve navbar con opción logout             │
└─────────────────────────────────────────────────────────────────┘
```

## 🔄 Flujo de Peticiones Autenticadas (Después del Login)

```
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND                                 │
│                                                                   │
│  PostService.getAllPosts()                                       │
│     ↓                                                             │
│  HttpClient.get('/posts')                                        │
│     ↓                                                             │
│  JwtInterceptor.intercept()                                      │
│  ├─ Lee token de localStorage                                    │
│  ├─ Agrega header:                                               │
│  │  "Authorization": "Bearer eyJhbGc..."                         │
│  └─> Pasa request al servidor                                   │
│     ↓                                                             │
│  Request HTTP GET /posts                                         │
└─────────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────────┐
│                    BACKEND (Spring Boot)                         │
│                                                                   │
│  JwtTokenValidator.validateToken(token)                         │
│  ├─ Valida que el token sea válido                              │
│  ├─ Extrae información del usuario                              │
│  └─ Procesa la request si el token es válido                    │
│                                                                   │
│  GET /posts                                                      │
│  ✓ Retorna lista de posts                                       │
└─────────────────────────────────────────────────────────────────┘
                            ↓ Response
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND                                 │
│                                                                   │
│  PostsListComponent recibe posts y los muestra                  │
└─────────────────────────────────────────────────────────────────┘
```

## 🔒 Flujo de Rutas Protegidas

```
Usuario quiere acceder a /posts/create
         ↓
    Route Guard checa
    AuthGuard.canActivate()
         ↓
    ¿Auth = true? ───→ NO ─→ Redirige a /auth/login
         ↓
        SI
         ↓
    Permite que se cargue CreatePostComponent
```

## 📋 Flujo de Creación de Post (Completo)

```
Usuario autenticado hace click en "Create Post"
         ↓
CreatePostComponent cargado
         ↓
Formulario mostrado con:
- Title (input)
- Description (textarea)
- Artist selector (dropdown)
- Genre selector (dropdown)
         ↓
Usuario llena el formulario
         ↓
Click en "Create Post"
         ↓
CreatePostComponent.onCreatePost()
         ↓
PostService.createPost(postData)
         ↓
         ┌──────────────────────────────┐
         │    JwtInterceptor            │
         │ Agrega token en header       │
         └──────────────────────────────┘
         ↓
POST /posts
{
  "title": "Great song discovered",
  "description": "Amazing track",
  "artistId": 1,
  "genreId": 2
}
Header: Authorization: Bearer token
         ↓
         ┌──────────────────────────────┐
         │      BACKEND                 │
         │ PostController.create()      │
         │ Valida token                 │
         │ Crea el post                 │
         │ Retorna PostResponse         │
         └──────────────────────────────┘
         ↓
Frontend recibe respuesta
         ↓
Muestra mensaje "Post creado"
         ↓
Redirige a /posts/{newPostId}
         ↓
PostDetailComponent cargado
         ↓
Post mostrado con comentarios y likes
```

## 🗑️ Flujo de Eliminación de Post (Con Validación)

```
Usuario hace click en delete
         ↓
DeletePost(postId)
         ↓
JwtInterceptor agrega token
         ↓
DELETE /posts/{postId}
Header: Authorization: Bearer token
         ↓
         ┌──────────────────────────────┐
         │      BACKEND                 │
         │ Valida token                 │
         │ Verifica que el usuario sea  │
         │ el propietario del post      │
         │ Elimina el post              │
         │ Retorna 204 No Content       │
         └──────────────────────────────┘
         ↓
Frontend recibe respuesta 204
         ↓
Remueve post de la lista
         ↓
Muestra mensaje "Post eliminado"
```

## 💬 Flujo de Comentarios

```
Usuario en PostDetailComponent
         ↓
Ingresa comentario y hace click "Post Comment"
         ↓
CommentService.createComment(request)
         ↓
JwtInterceptor agrega token
         ↓
POST /comments
{
  "description": "Great post!",
  "postId": 5
}
         ↓
         ┌──────────────────────────────┐
         │      BACKEND                 │
         │ Valida token                 │
         │ Obtiene el usuario del token │
         │ Crea el comentario           │
         │ Retorna CommentResponse      │
         └──────────────────────────────┘
         ↓
Frontend recibe nuevo comentario
         ↓
Se agrega a la lista de comentarios
         ↓
Campo de texto se limpia
```

## ❤️ Flujo de Likes

```
Usuario hace click en ❤️ Like
         ↓
LikeService.toggleLikePost(postId)
         ↓
JwtInterceptor agrega token
         ↓
POST /likes/post/{postId}
Header: Authorization: Bearer token
         ↓
         ┌──────────────────────────────┐
         │      BACKEND                 │
         │ Si ya existe like → Elimina  │
         │ Si no existe → Crea          │
         │ Retorna contador total       │
         └──────────────────────────────┘
         ↓
Frontend recibe totalLikes actualizado
         ↓
Actualiza contador en la UI
```

## 🔑 Almacenamiento de Datos

### localStorage (Persistente)
```javascript
// Token JWT
localStorage.getItem('token')
→ 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'

// Usuario actual
localStorage.getItem('user')
→ {
    "id": 1,
    "username": "joao",
    "name": "João",
    "surname": "Arce",
    "email": "joao@example.com",
    "roles": ["USER"]
  }
```

### Memory (En memoria Angular)
```typescript
// AuthService BehaviorSubject
authService.currentUser$
→ Observable<User | null>

// Actualizado cada vez que se login/logout
```

## ⚠️ Manejo de Errores

```
Request a backend
         ↓
¿Token válido?
         ├─ NO: 401 Unauthorized
         │      └─ AuthService redirige a /auth/login
         │      └─ Limpia token y usuario
         ├─ NO: 403 Forbidden
         │      └─ Usuario sin permisos
         │      └─ Redirige a /not-authorized
         └─ SI: Procesar request
              ├─ ¿Datos válidos?
              │  ├─ NO: 400 Bad Request
              │  │      └─ Mostrar error en componente
              │  └─ SI: Procesar
              └─ ¿Servidor error?
                 ├─ SI: 500 Internal Server Error
                 │      └─ Mostrar mensaje genérico
                 └─ NO: Success (200, 201, 204...)
```

## 📊 Tokens JWT - Estructura

```
Header.Payload.Signature

1. Header:
{
  "alg": "HS256",
  "typ": "JWT"
}

2. Payload (contiene los claims):
{
  "sub": "joao",         // Username
  "iat": 1234567890,     // Issued at
  "exp": 1234571490      // Expiration
}

3. Signature:
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret_key
)

Resultado: JWT token enviado en Authorization header
Bearer: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 🔄 Refresh Token (Próxima mejora)

Actualmente, el token expira en el tiempo definido en el backend.  
Para futuros upgrades se podría implementar:

```typescript
// Si recibimos 401 (token expirado)
// 1. Intentar obtener nuevo token con refresh token
// 2. Si falla, redirigir a login
// 3. Automáticamente reintentar la request original
```

---

**Este flujo completo garantiza que:**
✅ Usuarios no autenticados no pueden crear/editar/eliminar  
✅ Tokens se envían automáticamente en cada request  
✅ Backend valida cada token  
✅ Errores se manejan apropiadamente  
✅ UI se actualiza en tiempo real  
✅ Sesión se mantiene a través de localStorage  
