# MusicShare Frontend

Un frontend moderno y completo para MusicShare, una plataforma para compartir y descubrir música con la comunidad.

## Características

✅ **Autenticación JWT** - Login y registro de usuarios  
✅ **Gestión de Posts** - Crear, leer, buscar y filtrar publicaciones musicales  
✅ **Sistema de Comentarios** - Comentar en posts con soporte para likes  
✅ **Sistema de Likes** - Dar like a posts y comentarios  
✅ **Búsqueda Avanzada** - Buscar posts por título, descripción, artista o género  
✅ **Interfaz Responsiva** - Diseño mobile-first y adaptable a todos los dispositivos  
✅ **Guardias de Rutas** - Protección de rutas y autorización  
✅ **Interceptores HTTP** - Agregan automáticamente tokens JWT a las peticiones  

## Requisitos Previos

- Node.js 18+ 
- npm 11+
- Angular CLI 21+

## Instalación

1. **Navega a la carpeta del frontend:**
```bash
cd frontend
```

2. **Instala las dependencias:**
```bash
npm install
```

3. **Configura la URL del backend** (opcional):
   - Abre `src/environments/environment.ts`
   - Modifica `apiUrl` si tu backend corre en otro puerto

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'  // Cambia esto si es necesario
};
```

## Ejecutar la Aplicación

### Desarrollo

```bash
npm start
```

La aplicación estará disponible en `http://localhost:4200`

### Build para Producción

```bash
npm run build
```

Los archivos compilados estarán en `dist/`

## Estructura del Proyecto

```
frontend/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── auth/           # Login y registro
│   │   │   ├── posts/          # Gestión de posts
│   │   │   └── shared/         # Navbar, footer, etc
│   │   ├── services/           # Servicios HTTP
│   │   ├── models/             # Interfaces TypeScript
│   │   ├── guards/             # Auth guard, admin guard
│   │   ├── interceptors/       # JWT interceptor
│   │   └── app.routes.ts       # Configuración de rutas
│   ├── environments/           # Configuración de ambiente
│   └── index.html
├── package.json
└── tsconfig.json
```

## API Endpoints Consumidos

El frontend se conecta con los siguientes endpoints del backend:

### Autenticación
- `POST /auth/sing-up` - Registro de usuarios
- `POST /auth/log-in` - Login de usuarios

### Posts
- `GET /posts` - Listar todos los posts (paginado)
- `GET /posts/:id` - Obtener un post por ID
- `POST /posts` - Crear un nuevo post
- `PUT /posts/:id` - Actualizar un post
- `DELETE /posts/:id` - Eliminar un post
- `GET /posts/search?q=query` - Buscar posts
- `GET /posts/user/:userId` - Posts de un usuario
- `GET /posts/artist/:artistId` - Posts de un artista
- `GET /posts/genre/:genreId` - Posts de un género

### Comentarios
- `POST /comments` - Crear un comentario
- `GET /comments/post/:postId` - Obtener comentarios de un post
- `DELETE /comments/:id` - Eliminar un comentario

### Likes
- `POST /likes/post/:postId` - Toggle like en un post
- `POST /likes/comment/:commentId` - Toggle like en un comentario

### Usuarios
- `GET /users/:id` - Obtener perfil de usuario
- `GET /users/me` - Obtener mi perfil
- `DELETE /users/:id` - Eliminar cuenta

### Artistas
- `GET /artists` - Listar todos los artistas
- `GET /artists/:id` - Obtener un artista
- `POST /artists` - Crear un artista (admin)
- `PUT /artists/:id` - Actualizar un artista (admin)
- `DELETE /artists/:id` - Eliminar un artista (admin)

### Géneros
- `GET /genres` - Listar todos los géneros
- `GET /genres/:id` - Obtener un género
- `POST /genres` - Crear un género (admin)
- `PUT /genres/:id` - Actualizar un género (admin)
- `DELETE /genres/:id` - Eliminar un género (admin)

## Autenticación

El frontend utiliza autenticación JWT (JSON Web Tokens):

1. Cuando el usuario hace login, recibe un token JWT
2. El token se almacena en localStorage
3. El `JwtInterceptor` automáticamente agrega el token en el header `Authorization: Bearer <token>` de todas las peticiones
4. Rutas protegidas usan `AuthGuard` para verificar si el usuario está autenticado

## Componentes Principales

### LoginComponent
- Formulario de login
- Validación de credenciales
- Redirección automática a posts después del login

### RegisterComponent
- Formulario de registro completo
- Validación de datos
- Confirmación de registro

### PostsListComponent
- Listado paginado de posts
- Búsqueda en tiempo real
- Toggle de likes
- Navegación a detalle de post

### PostDetailComponent
- Vista completa de un post
- Comentarios paginados
- Agregar comentarios
- Likes en posts y comentarios

### NavbarComponent
- Navegación principal
- Menú responsive
- Info del usuario autenticado
- Botón de logout

### FooterComponent
- Información de la aplicación
- Links útiles
- Redes sociales

## Desarrollo

### Crear un nuevo componente

```bash
ng generate component components/mycomponent
```

### Crear un nuevo servicio

```bash
ng generate service services/myservice
```

### Crear un nuevo guard

```bash
ng generate guard guards/myguard
```

## Testing (Próximamente)

```bash
npm run test
```

## Deployment

El frontend puede ser deployeado en cualquier servidor estático:

1. **Build la aplicación:**
```bash
npm run build
```

2. **Despliega los archivos en `dist/`** a tu servidor de hosting

### Ejemplo con Netlify:
```bash
npm install -g netlify-cli
npm run build
netlify deploy --prod --dir=dist
```

### Ejemplo con Vercel:
```bash
npm install -g vercel
npm run build
vercel --prod
```

## Soporte

Para reportar bugs o sugerir mejoras, abre un issue en el repositorio.

## Licencia

MIT
