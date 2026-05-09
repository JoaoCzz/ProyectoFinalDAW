# 🗺️ Mapa Visual del Proyecto MusicShare

## 📊 Diagrama de Alto Nivel

```
═══════════════════════════════════════════════════════════════════
                        MUSICSHARE PROJECT
═══════════════════════════════════════════════════════════════════

┌─────────────────────────────────┐    ┌─────────────────────────────┐
│      FRONTEND (Angular 21)      │    │   BACKEND (Spring Boot)      │
│                                 │    │                              │
│  • 12 Componentes               │    │  • 7 Controladores          │
│  • 7 Servicios HTTP             │    │  • 8 Repositorios           │
│  • 6 Modelos TypeScript         │    │  • Base de Datos (MySQL)    │
│  • Autenticación JWT            │◄──►│  • JWT Validation           │
│  • Responsive Design            │    │  • Business Logic           │
│  • Port: 4200                   │    │  • Port: 8080               │
└─────────────────────────────────┘    └─────────────────────────────┘
```

## 🏗️ Estructura del Frontend

```
frontend/
│
├── src/
│   ├── app/
│   │   ├── 📁 models/
│   │   │   ├── auth.model.ts ...................... Usuarios, Login
│   │   │   ├── post.model.ts ...................... Posts
│   │   │   ├── comment.model.ts ................... Comentarios
│   │   │   ├── like.model.ts ...................... Likes
│   │   │   ├── artist.model.ts .................... Artistas
│   │   │   └── genre.model.ts ..................... Géneros
│   │   │
│   │   ├── 📁 services/
│   │   │   ├── auth.service.ts .................... 🔐 Autenticación
│   │   │   ├── post.service.ts .................... 📝 Posts CRUD
│   │   │   ├── comment.service.ts ................. 💬 Comentarios
│   │   │   ├── like.service.ts .................... ❤️ Likes
│   │   │   ├── artist.service.ts .................. 🎤 Artistas
│   │   │   ├── genre.service.ts ................... 🎵 Géneros
│   │   │   └── user.service.ts .................... 👤 Usuarios
│   │   │
│   │   ├── 📁 guards/
│   │   │   ├── auth.guard.ts ...................... Protege rutas
│   │   │   └── admin.guard.ts ..................... Protege admin
│   │   │
│   │   ├── 📁 interceptors/
│   │   │   └── jwt.interceptor.ts ................  Agrega token JWT
│   │   │
│   │   ├── 📁 components/
│   │   │   ├── 📁 auth/
│   │   │   │   ├── login.component.ts/html/css
│   │   │   │   └── register.component.ts/html/css
│   │   │   │
│   │   │   ├── 📁 posts/
│   │   │   │   ├── posts-list.component.ts/html/css
│   │   │   │   ├── post-detail.component.ts/html/css
│   │   │   │   └── create-post.component.ts/html/css
│   │   │   │
│   │   │   └── 📁 shared/
│   │   │       ├── navbar.component.ts/html/css
│   │   │       ├── footer.component.ts/html/css
│   │   │       └── not-authorized.component.ts
│   │   │
│   │   ├── app.routes.ts ......................... Definición de rutas
│   │   ├── app.config.ts ......................... Configuración Angular
│   │   ├── app.ts ............................... Componente raíz
│   │   ├── app.html ............................. Template principal
│   │   └── app.css .............................. Estilos main
│   │
│   ├── 📁 environments/
│   │   ├── environment.ts ........................ Config desarrollo
│   │   └── environment.prod.ts .................. Config producción
│   │
│   ├── styles.css ............................... Estilos globales
│   ├── index.html ............................... HTML base
│   └── main.ts ................................. Entry point
│
├── package.json ................................. Dependencias
├── tsconfig.json ................................ Config TypeScript
├── angular.json ................................. Config Angular
└── README.md .................................... Documentación


DOCUMENTACIÓN:
├── 📄 QUICK_START.md ............................ ⚡ Setup rápido
├── 📄 FRONTEND_README.md ........................ 📖 Documentación técnica
├── 📄 README_FRONTEND.md ........................ 🎉 Resumen ejecutivo
├── 📄 AUTHENTICATION_FLOW.md .................... 🔐 Diagramas JWT
├── 📄 INSTALLATION_CHECKLIST.md ................ ✅ Verificación
└── 📄 DOCUMENTATION_INDEX.md ................... 📚 Índice de docs
```

## 🔄 Flujo de Datos

```
                     ┌──────────────────┐
                     │   USER ACTION    │
                     │  (Click, Input)  │
                     └────────┬─────────┘
                              │
                              ▼
                     ┌──────────────────┐
                     │   COMPONENT      │
                     │ (*.component.ts) │
                     └────────┬─────────┘
                              │
                              ▼
                     ┌──────────────────┐
                     │    SERVICE       │
                     │  (*.service.ts)  │
                     └────────┬─────────┘
                              │
                              ▼
                     ┌──────────────────┐
                     │  INTERCEPTOR     │
                     │ (jwt.interceptor)│
                     │ + token JWT      │
                     └────────┬─────────┘
                              │
                              ▼
                     ┌──────────────────┐
                     │  HTTP REQUEST    │
                     │  POST, GET, etc  │
                     └────────┬─────────┘
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          ▼                   ▼                   ▼
     ┌─────────┐         ┌─────────┐       ┌──────────┐
     │ BACKEND │◄────────│ DATABASE│       │ JWT VALID│
     │ Valida  │         │ MySQL   │       │   ?      │
     │ Token   │         └─────────┘       └────┬─────┘
     └────┬────┘                                │
          │                                     │
          └──────────────────┬──────────────────┘
                             │
                    ┌────────▼────────┐
                    │ ¿Token Válido?  │
                    └────┬─────────┬──┘
                         │         │
                        YES        NO
                         │         │
                         ▼         ▼
                    ┌────────┐ ┌──────────┐
                    │Process │ │401/403   │
                    │Request │ │Error     │
                    └────┬───┘ └────┬─────┘
                         │          │
                         ▼          ▼
                    ┌────────────────────────┐
                    │  HTTP RESPONSE         │
                    │  200/201/204/400/401/.. │
                    └────────┬───────────────┘
                             │
                             ▼
                    ┌─────────────────────┐
                    │  COMPONENT recibe   │
                    │  datos              │
                    └────────┬────────────┘
                             │
                             ▼
                    ┌─────────────────────┐
                    │  UI se actualiza    │
                    │  (template binding) │
                    └─────────────────────┘
```

## 🗺️ Mapeo de Rutas

```
NAVEGACIÓN DEL USUARIO:

Visitante
   │
   ├─ /                    → /posts (redirige)
   │
   ├─ /auth/login ◄────────┐
   │   LoginComponent       │
   │   [Login Form]         │
   │       │                │
   │       └────Success────►│ /posts
   │                        │
   │                    Usuario Autenticado
   │                        │
   ├─ /auth/register        │
   │   RegisterComponent    │
   │   [Register Form]      │
   │       │                │
   │       └────Success────►│ /auth/login
   │                        │
   │                        ├─ /posts ◄──────────────┐
   │                        │   PostsListComponent   │
   │                        │   • Listado pagina do  │
   │                        │   • Búsqueda           │
   │                        │   • Botón crear        │
   │                        │       │                │
   │                        │       └─Click───────┐  │
   │                        │                     │  │
   │                        ├─ /posts/create      │  │
   │                        │   CreatePostComponent  │
   │                        │   [Form]               │
   │                        │       │                │
   │                        │       └─Success───►/posts/:id
   │                        │                     │
   │                        ├─ /posts/:id ◄──────┘
   │                        │   PostDetailComponent
   │                        │   • Post detalle
   │                        │   • Comentarios
   │                        │   • Like button
   │                        │       │
   │                        │       └─Click────►/posts
   │                        │
   │                        └─ Navbar →  Logout
   │                                       │
   └◄──────────────────────────────────────┘
```

## 💻 Stack Técnico

```
FRONTEND STACK:
┌─────────────────────────────────┐
│      Frontend (4200)            │
├─────────────────────────────────┤
│  Angular 21                     │
│  TypeScript 5.9                 │
│  RxJS 7.8                       │
│  CSS3 (Responsive)              │
├─────────────────────────────────┤
│       HTTP Client               │
│  (comunicación con backend)     │
└─────────────┬───────────────────┘
              │
              │ HTTP Requests
              │ (JWT Bearer Token)
              │
┌─────────────▼───────────────────┐
│      Backend (8080)             │
├─────────────────────────────────┤
│  Spring Boot                    │
│  Java 21                        │
│  Spring Security (JWT)          │
│  Spring Data JPA                │
├─────────────────────────────────┤
│    REST API                     │
│    (JSON responses)             │
└─────────────┬───────────────────┘
              │
┌─────────────▼───────────────────┐
│   Database (3306)               │
├─────────────────────────────────┤
│  MySQL 8.0                      │
│  Tables:                        │
│  • users                        │
│  • posts                        │
│  • comments                     │
│  • likes_post                   │
│  • likes_comment                │
│  • artists                      │
│  • genres                       │
└─────────────────────────────────┘
```

## 🎯 Componentes y sus Responsabilidades

```
┌─────────────────────────────────────────────────────────────┐
│                    COMPONENTES PRINCIPALES                  │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  📱 NavbarComponent                                          │
│  ├─ Logo                                                    │
│  ├─ Menu de navegación                                      │
│  ├─ Info del usuario (si autenticado)                      │
│  └─ Botón logout                                            │
│                                                              │
│  🔐 LoginComponent                                           │
│  ├─ Formulario: username, password                         │
│  ├─ Validación                                              │
│  └─ Llamada a AuthService.login()                          │
│                                                              │
│  📝 RegisterComponent                                       │
│  ├─ Formulario: username, password, email, name, etc      │
│  ├─ Validación                                              │
│  └─ Llamada a AuthService.register()                       │
│                                                              │
│  📋 PostsListComponent                                      │
│  ├─ Listado de posts paginado                             │
│  ├─ Campo de búsqueda                                       │
│  ├─ Botones de Like                                         │
│  └─ Navegación a PostDetailComponent                       │
│                                                              │
│  ✏️ CreatePostComponent                                     │
│  ├─ Formulario: título, descripción, artista, género      │
│  ├─ Validación                                              │
│  └─ LLamada a PostService.createPost()                     │
│                                                              │
│  👁️ PostDetailComponent                                     │
│  ├─ Post completo                                           │
│  ├─ Lista de comentarios                                    │
│  ├─ Formulario para comentar                               │
│  ├─ Botones de Like (post y comentarios)                   │
│  └─ Opción de eliminar (solo propietario)                  │
│                                                              │
│  🦶 FooterComponent                                         │
│  ├─ Info empresa                                            │
│  ├─ Enlaces útiles                                          │
│  └─ Copyright                                               │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## 🔗 Conexión de Servicios

```
                      AUTH SERVICE
                           │
              ┌────────────┼────────────┐
              │            │            │
              ▼            ▼            ▼
          POST /login   GET /users/me   logout()
              │            │
              └────────────┼────────────┘
                           │
                  (Almacena token)
                           │
        JwtInterceptor agrega a todas las peticiones
                           │
          ┌────┬────┬────┬─┴─┬────┬────┐
          │    │    │    │    │    │    │
          ▼    ▼    ▼    ▼    ▼    ▼    ▼
        POST POST GET  PUT  DEL  GET  GET
        /comments /likes /users /users /artists /genres
```

## 🚀 Flujo de Inicio

```
1. npm start
        ▼
2. Angular compila (main.ts)
        ▼
3. Carga AppComponent (app.ts)
        ▼
4. Renderiza app.html
   ├─ <app-navbar />
   ├─ <router-outlet /> (aquí van los componentes de rutas)
   └─ <app-footer />
        ▼
5. Router evalúa la ruta actual
        ▼
6. Verifica Guards (AuthGuard, AdminGuard)
        ▼
7. Carga el componente de la ruta
        ▼
8. Component hace subscribe a datos
   (llama servicios que hacen HTTP requests)
        ▼
9. Frontend lista para interacción
```

---

**Este mapa visual te ayuda a entender la estructura completa del proyecto.** 🗺️
