# 📚 Índice de Documentación - MusicShare Frontend

Bienvenido a MusicShare. Aquí encontrarás acceso rápido a toda la documentación.

---

## 🚀 Comienza Aquí

### Para Empezar Rápidamente
👉 **[QUICK_START.md](QUICK_START.md)** - 5 minutos para estar listo
- Instalación de dependencias
- Ejecución del servidor
- Primeros pasos

### Overview del Proyecto
👉 **[README_FRONTEND.md](README_FRONTEND.md)** - Resumen ejecutivo
- Qué se creó
- Características principales
- Setup rápido

---

## 📖 Documentación Detallada

### Instalación y Configuración
👉 **[FRONTEND_README.md](frontend/FRONTEND_README.md)** - Documentación técnica
- Requisitos previos
- Instalación paso a paso
- Estructura del proyecto
- Endpoints API
- Deployment

### Verificación de Instalación
👉 **[INSTALLATION_CHECKLIST.md](INSTALLATION_CHECKLIST.md)** - Checklist completo
- Pre-instalación
- Durante la instalación
- Verificación final
- Troubleshooting

### Flujos de Datos
👉 **[AUTHENTICATION_FLOW.md](AUTHENTICATION_FLOW.md)** - Diagramas y flujos
- Flujo de autenticación JWT
- Flujo de peticiones HTTP
- Flujo de rutas protegidas
- Flujo de operaciones CRUD
- Handling de errores

---

## 🎯 Guías por Tema

### Autenticación
```
Información:
├─ QUICK_START.md → "Autenticación" section
├─ AUTHENTICATION_FLOW.md → Diagramas completos
└─ FRONTEND_README.md → API Reference

Ubicación en código:
├─ src/app/services/auth.service.ts
├─ src/app/guards/auth.guard.ts
├─ src/app/interceptors/jwt.interceptor.ts
└─ src/app/components/auth/
```

### Gestión de Posts
```
Información:
├─ QUICK_START.md → "Características" section
└─ FRONTEND_README.md → API Reference

Ubicación en código:
├─ src/app/services/post.service.ts
├─ src/app/models/post.model.ts
└─ src/app/components/posts/
    ├─ posts-list.component.ts
    ├─ post-detail.component.ts
    └─ create-post.component.ts
```

### Comentarios y Likes
```
Información:
├─ QUICK_START.md → "Características" section
└─ FRONTEND_README.md → API Reference

Ubicación en código:
├─ src/app/services/comment.service.ts
├─ src/app/services/like.service.ts
├─ src/app/models/comment.model.ts
├─ src/app/models/like.model.ts
└─ src/app/components/posts/post-detail.component.ts
```

### Diseño Responsive
```
Información:
├─ README_FRONTEND.md → "Compatibilidad" section
├─ QUICK_START.md → "Estilos" section
└─ FRONTEND_README.md → CSS y diseño

Ubicación en código:
├─ src/styles.css → Global styles
├─ src/app/app.css → Main layout
└─ Cada componente tiene su .css con media queries
```

---

## 💻 Rutas del Proyecto

### Login y Registro
```
/auth/login    → LoginComponent
/auth/register → RegisterComponent

Usuarios sin autenticar ven:
- Formulario de login
- Link a registro
- Link a login

Usuarios autenticados:
- Son redirigidos a /posts automáticamente
```

### Posts (Publicaciones)
```
/posts              → PostsListComponent (listado con búsqueda)
/posts/create       → CreatePostComponent (requiere autenticación)
/posts/:id          → PostDetailComponent (detalle + comentarios)

Búsqueda:
- Por título
- Por descripción
- Por artista
- Por género
```

---

## 🔧 Archivos Importantes

### Configuración del Backend
```
src/environments/environment.ts
- Contiene apiUrl
- Cambiar aquí si tu backend está en otro puerto
```

### Rutas
```
src/app/app.routes.ts
- Define todas las rutas
- Configura guards
```

### Estilos Globales
```
src/styles.css
- Variables CSS
- Utilidades
- Diseño responsivo
```

---

## 📊 Mapeo de Componentes a Documentación

| Componente | Archivo | Documentación |
|---|---|---|
| Login | `components/auth/login.component.ts` | AUTHENTICATION_FLOW.md |
| Register | `components/auth/register.component.ts` | AUTHENTICATION_FLOW.md |
| Posts List | `components/posts/posts-list.component.ts` | QUICK_START.md |
| Post Detail | `components/posts/post-detail.component.ts` | QUICK_START.md |
| Create Post | `components/posts/create-post.component.ts` | QUICK_START.md |
| Navbar | `components/shared/navbar.component.ts` | README_FRONTEND.md |
| Footer | `components/shared/footer.component.ts` | README_FRONTEND.md |

---

## 🔍 Búsqueda Rápida

### Quiero saber...

**Cómo iniciar**
→ [QUICK_START.md](QUICK_START.md)

**Cómo instalar**
→ [INSTALLATION_CHECKLIST.md](INSTALLATION_CHECKLIST.md)

**Cómo funciona la autenticación**
→ [AUTHENTICATION_FLOW.md](AUTHENTICATION_FLOW.md)

**Todos los endpoints**
→ [FRONTEND_README.md](frontend/FRONTEND_README.md) (Sección "API Endpoints")

**Estructura del proyecto**
→ [QUICK_START.md](QUICK_START.md) (Sección "Primeros Pasos")

**Cómo cambiar la URL del backend**
→ [README_FRONTEND.md](README_FRONTEND.md) (Sección "Configuración de Backend")

**Qué hacer si hay errores**
→ [INSTALLATION_CHECKLIST.md](INSTALLATION_CHECKLIST.md) (Sección "Troubleshooting")

**Cómo deployar**
→ [FRONTEND_README.md](frontend/FRONTEND_README.md) (Sección "Deployment")

---

## 🎓 Aprendizaje

Si quieres entender cómo funciona todo:

1. **Primero lee:** README_FRONTEND.md
2. **Luego lee:** QUICK_START.md
3. **Entender flujos:** AUTHENTICATION_FLOW.md
4. **Implementar cambios:** Mira la estructura en QUICK_START.md

---

## 🚀 Comandos Útiles

```bash
# Ir a la carpeta
cd frontend

# Instalar dependencias
npm install

# Iniciar servidor de desarrollo
npm start

# Build para producción
npm run build

# Limpiar e reinstalar
rm -rf node_modules package-lock.json && npm install
```

---

## 📋 Verificación Final

Use esta checklist:
→ [INSTALLATION_CHECKLIST.md](INSTALLATION_CHECKLIST.md)

---

## 💡 Tips

- **Recuerda:** Tu backend debe estar corriendo en `http://localhost:8080`
- **Cambios rápidos:** Edita archivos `.ts` o `.html` y el servidor auto-recompila
- **Debug:** Abre F12 en el navegador para ver errores
- **Red:** F12 → Network tab para ver peticiones al backend
- **Storage:** F12 → Application → localStorage para ver el token JWT

---

## 🆘 Necesitas Ayuda?

1. Revisa [INSTALLATION_CHECKLIST.md](INSTALLATION_CHECKLIST.md) - Troubleshooting
2. Revisa la consola del navegador (F12)
3. Revisa que el backend esté corriendo
4. Verifica la URL en `environment.ts`

---

## 📞 Resumen Rápido de Documentos

| Documento | Para Qué | Lectura |
|---|---|---|
| README_FRONTEND.md | Overview general | 5 min |
| QUICK_START.md | Setup e inicios | 10 min |
| INSTALLATION_CHECKLIST.md | Verificar todo funciona | 15 min |
| AUTHENTICATION_FLOW.md | Entender JWT | 15 min |
| FRONTEND_README.md | Referencia técnica | 30 min |

---

## ✨ Estado del Proyecto

✅ **Código:** Completo
✅ **Funcionalidades:** Todas implementadas
✅ **Seguridad:** JWT configurado
✅ **Documentación:** Completa
✅ **Testing:** Estructura lista
✅ **Listo para:** Producción

---

**¡Bienvenido a MusicShare! Comienza con [QUICK_START.md](QUICK_START.md) 🚀**
