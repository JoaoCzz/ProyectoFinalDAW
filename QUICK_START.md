# Guía de Inicio Rápido - MusicShare Frontend

## ¿Qué se ha creado?

Se ha creado un frontend completo en Angular 21 que se conecta perfectamente con tu backend.

## 📁 Lo que encontrarás en la carpeta `frontend/src/app/`:

### 🔐 Autenticación
- **`components/auth/`** - Componentes de Login y Register
- **`services/auth.service.ts`** - Gestión de autenticación y tokens
- **`guards/`** - Guards para proteger rutas

### 📝 Posts (Publicaciones)
- **`components/posts/`** - Listado, crear, ver detalles de posts
- **`services/post.service.ts`** - API calls para posts
- Búsqueda integrada
- Paginación

### 💬 Comentarios
- **`components/posts/post-detail.component`** - Comentarios en posts
- **`services/comment.service.ts`** - Gestión de comentarios
- Agregar, eliminar, listar comentarios

### ❤️ Likes
- **`services/like.service.ts`** - Toggle de likes
- Likes en posts y comentarios
- Contador de likes en tiempo real

### 🎨 Componentes Compartidos
- **`components/shared/navbar.component`** - Navegación principal
- **`components/shared/footer.component`** - Pie de página
- **`components/shared/not-authorized.component`** - Página de acceso denegado

### 🌐 Servicios HTTP
Todos ubicados en `services/`:
- `auth.service.ts` - Autenticación
- `post.service.ts` - Posts
- `comment.service.ts` - Comentarios
- `like.service.ts` - Likes
- `artist.service.ts` - Artistas
- `genre.service.ts` - Géneros
- `user.service.ts` - Usuarios

### 🛡️ Seguridad
- **`interceptors/jwt.interceptor.ts`** - Agrega automáticamente el token JWT a las peticiones
- **`guards/auth.guard.ts`** - Protege rutas que requieren autenticación
- **`guards/admin.guard.ts`** - Protege rutas de admin

### 📱 Modelos
Tipos TypeScript en `models/`:
- `auth.model.ts` - Usuarios, login, registro
- `post.model.ts` - Posts
- `comment.model.ts` - Comentarios
- `like.model.ts` - Likes
- `artist.model.ts` - Artistas
- `genre.model.ts` - Géneros

## 🚀 Primeros Pasos

### 1. Instala dependencias
```bash
cd frontend
npm install
```

### 2. Inicia el servidor de desarrollo
```bash
npm start
```

La aplicación abrirá en `http://localhost:4200`

### 3. Verifica que el backend esté corriendo
Asegúrate de que tu backend corre en `http://localhost:8080`

## 🔗 Conexión Backend-Frontend

La configuración está en `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'  // Cambiar si tu backend corre en otro puerto
};
```

Todos los servicios usan `http://localhost:8080` como base para las peticiones.

## 🎯 Características Principales

✅ **Autenticación JWT** - Login seguro con tokens  
✅ **Gestión de Posts** - CRUD completo  
✅ **Comentarios** - Agregar, ver, eliminar comentarios  
✅ **Sistema de Likes** - Like/Unlike posts y comentarios  
✅ **Búsqueda** - Buscar posts por título, artista, género  
✅ **Paginación** - Navegación entre páginas de posts y comentarios  
✅ **Responsive Design** - Funciona en mobile, tablet y desktop  
✅ **Guardias de Rutas** - Acceso controlado según rol de usuario  

## 📊 Flujo de Datos

1. Usuario inicia sesión → Se guarda el token JWT
2. JWT se envía automáticamente en cada petición (mediante JwtInterceptor)
3. Backend valida el token y retorna los datos
4. Frontend actualiza la UI con los datos recibidos
5. Sistema reactivo con RxJS para actualizaciones en tiempo real

## 🎨 Estilos

- **Colores**: Gradientes morados y azules (#667eea, #764ba2)
- **Fuente**: Inter
- **Diseño**: Mobile-first, responsive
- Estilos globales en `src/styles.css`

## 🔧 Estructura de Componentes

Todos los componentes son **standalone** (sin módulos):

```
Component
├── Template (.html)
├── Lógica (.ts) 
└── Estilos (.css)
```

Con imports directos de Angular en cada componente.

## 📚 Rutas Disponibles

```
/                  → Redirige a /posts
/auth/login        → Login
/auth/register     → Registro
/posts             → Listado de posts
/posts/create      → Crear post (requiere autenticación)
/posts/:id         → Detalle del post con comentarios
/not-authorized    → Acceso denegado
```

## 🔒 Rutas Protegidas

Requieren estar autenticado:
- `/posts/create` - Crear post
- Comentar en posts
- Dar likes

## ⚙️ Configuración Importante

### Para cambiar la URL del backend:
1. Abre `src/environments/environment.ts`
2. Modifica `apiUrl`

### Para deployment:
1. Build: `npm run build`
2. Sube los archivos de `dist/` a tu servidor

## 🐛 Troubleshooting

### "No puedo conectar al backend"
- Verifica que el backend corre en `http://localhost:8080`
- Revisa `src/environments/environment.ts`
- Comprueba la consola del navegador (F12) para errores

### "El login no funciona"
- Asegúrate de que el usuario existe en la base de datos
- Verifica que el email está correctamente configurado en el backend

### "CORS error"
- El backend debe tener CORS habilitado para `http://localhost:4200`

## 📞 Entidades del Backend que se Consumen

El frontend consume exactamente estas entidades que vimos en tu backend:

✓ User (Usuario)  
✓ Post (Publicación)  
✓ Comment (Comentario)  
✓ Like (Like en posts y comentarios)  
✓ Artist (Artista)  
✓ Genre (Género)  

Con todos sus endpoints mapeados correctamente.

## 🎓 Próximos Pasos (Opcionales)

1. Agregar validación más estricta de formularios
2. Implementar caché de datos
3. Agregar animaciones
4. Unit tests con Jasmine
5. E2E tests con Cypress/Playwright
6. PWA (offline support)
7. Dark mode

---

¡El frontend está listo para usar! Simplemente ejecuta `npm start` y comienza a desarrollar. 🎉
