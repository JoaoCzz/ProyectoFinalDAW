# ✅ Checklist de Instalación y Verificación

## 📋 Pre-Instalación

- [ ] Node.js 18+ instalado (`node -v`)
- [ ] npm 11+ instalado (`npm -v`)
- [ ] Git instalado
- [ ] Tu backend corriendo en `http://localhost:8080`
- [ ] Base de datos del backend configurada y disponible

## 📦 Instalación

- [ ] Cloná o descargaste el proyecto
- [ ] Abriste la carpeta `frontend` en VS Code
- [ ] Ejecutaste `npm install` sin errores
- [ ] No hay mensajes de error en la instalación
- [ ] Carpeta `node_modules` creada correctamente
- [ ] Archivo `package-lock.json` generado

## 🚀 Inicio del Servidor

- [ ] Ejecutaste `npm start` en la carpeta `frontend`
- [ ] No hay errores de compilación
- [ ] El navegador se abre automáticamente en `http://localhost:4200`
- [ ] Puedes ver la página de login
- [ ] NavigBar cargó correctamente
- [ ] Footer visible en la parte inferior

## 🔗 Conectividad Backend

- [ ] Backend está corriendo (`http://localhost:8080`)
- [ ] Abriste la consola del navegador (F12)
- [ ] No hay errores de CORS en la consola
- [ ] No hay errores de red (red tab) en F12
- [ ] Intentaste hacer login
- [ ] Ves una petición POST a `http://localhost:8080/auth/log-in` en la red
- [ ] La respuesta tiene token JWT

## 👤 Prueba de Autenticación

- [ ] Creaste una cuenta nueva (Register)
- [ ] Datos validados correctamente
- [ ] Recibiste confirmación de registro
- [ ] Pudiste hacer login con esas credenciales
- [ ] Fuiste redirigido a `/posts` después del login
- [ ] Ves tu username en el navbar
- [ ] El localStorage tiene un token guardado (F12 → Application → localStorage)

## 📝 Prueba de Posts

- [ ] Puedes ver la lista de posts
- [ ] Los posts se loaded correctamente
- [ ] Haciendo click en un post te lleva al detalle
- [ ] Puedes hacer click en un post sin error
- [ ] Ves los comentarios del post
- [ ] El contador de comentarios es correcto
- [ ] El contador de likes es correcto

## ➕ Crear Post

- [ ] Haciendo click en "Create Post" funciona
- [ ] Solo aparece si estás autenticado
- [ ] El formulario tiene: Title, Description, Artist, Genre
- [ ] Los dropdowns de Artist y Genre se llenan correctamente
- [ ] Puedes crear un post sin error
- [ ] Eres redirigido al detalle del post nuevo
- [ ] El post aparece en la lista de posts

## 💬 Comentarios

- [ ] Puedes ver comentarios en un post
- [ ] Ves el nombre del autor del comentario
- [ ] Ves la fecha del comentario
- [ ] Puedes escribir un comentario (si estás autenticado)
- [ ] El comentario se agrega a la lista
- [ ] Puedes eliminar tus propios comentarios
- [ ] No puedes eliminar comentarios de otros

## ❤️ Sistema de Likes

- [ ] Puedes hacer like a un post (botón ❤️)
- [ ] El contador aumenta
- [ ] Puedes hacer unlike (vuelve a bajar)
- [ ] Puedes hacer like a comentarios
- [ ] El contador de likes de comentarios se actualiza
- [ ] Los usuarios no autenticados no pueden dar like

## 🔍 Búsqueda

- [ ] El campo de búsqueda es visible
- [ ] Puedes escribir en el campo
- [ ] El botón Search funciona
- [ ] Ves resultados actualizados
- [ ] La paginación funciona con resultados de búsqueda
- [ ] Búsqueda vacía retorna todos los posts

## 📱 Responsive Design

**En escritorio:**
- [ ] Navbar se ve bien
- [ ] Posts se muestran en grid
- [ ] Formularios se ven correctos
- [ ] Footer está abajo

**En tablet (iPad, 768px):**
- [ ] Menu hamburger funciona
- [ ] Posts se ven en 2 columnas
- [ ] Formularios son legibles
- [ ] No hay scroll horizontal

**En mobile (teléfono, 375px):**
- [ ] Menu hamburger está presente
- [ ] Al hacer click abre/cierra
- [ ] Posts en 1 columna
- [ ] Botones del tamaño correcto
- [ ] Formularios tienen espacio suficiente
- [ ] No hay overflow de texto

## 🔐 Autenticación y Seguridad

- [ ] Logout funciona
- [ ] Al logout se limpia localStorage
- [ ] Al estar deslogueado no puedes crear posts
- [ ] Al estar deslogueado no puedes comentar
- [ ] Les redirige a login si intentas rutas protegidas
- [ ] El token se envía en cada petición (F12 → Network, Ver headers)
- [ ] Header `Authorization: Bearer <token>` está presente

## 🛂 Guards y Autorización

- [ ] Solo usuarios autenticados ven "Create Post"
- [ ] AuthGuard protege `/posts/create`
- [ ] Al ir a `/posts/create` sin login → redirige a login
- [ ] Los Guards funcionan correctamente
- [ ] Página `/not-authorized` funciona

## 🎨 Estilos y Diseño

- [ ] Colores: Gradientes morado-azul
- [ ] Fonts: Inter se cargó correctamente
- [ ] Botones tienen hover effect
- [ ] Input tiene focus effect
- [ ] Mensajes de error se ven en rojo
- [ ] Mensajes de éxito se ven en verde
- [ ] Spinner de carga se muestra cuando corresponde
- [ ] No hay flickering de contenido

## 📊 Performance

- [ ] La aplicación se inicia rápidamente
- [ ] Los posts se cargan sin mucho delay
- [ ] Las búsquedas son rápidas
- [ ] No hay memory leaks (verificar DevTools)
- [ ] Las transiciones son suaves

## ❌ Prueba de Errores

- [ ] Intenta login con credenciales inválidas → error correcto
- [ ] Intenta registro con datos inválidos → error validación
- [ ] Intenta crear post sin artista → error
- [ ] Desconecta internet y ve la respuesta → error manejo
- [ ] Mata el backend y ve la respuesta → error manejo

## 📚 Documentación

- [ ] Leíste `FRONTEND_README.md`
- [ ] Leíste `QUICK_START.md`
- [ ] Leíste `AUTHENTICATION_FLOW.md`
- [ ] Entiendes cómo funciona el JWT
- [ ] Sabes dónde cambiar la URL del backend si es necesario

## 🔧 Configuración (si es necesario)

- [ ] Si el backend está en otro puerto, actualizaste `environment.ts`
- [ ] Verificaste que `apiUrl` apunta al backend correcto
- [ ] Compilaste de nuevo después de cambios

## 📝 Pruebas Finales

- [ ] Hiciste un flujo completo: Register → Login → Create Post → Comment → Like
- [ ] Todo funcionó sin errores
- [ ] Puedes navegar sin problemas
- [ ] No hay errores en la consola (F12 → Console)
- [ ] La red es limpia (F12 → Network, sin 404s o 500s)

## 🎉 Deployment Ready

- [ ] Frontend está listo para producción
- [ ] Documentación está completa
- [ ] Códigos comentados donde es necesario
- [ ] No hay archivos innecesarios

---

## 🚨 Troubleshooting

Si algo no funciona, revisa:

### No puedo iniciar el servidor
```bash
# Limpia node_modules y reinstala
rm -rf node_modules package-lock.json
npm install
npm start
```

### No puedo connectar al backend
- Verifica que el backend esté corriendo: `curl http://localhost:8080`
- Revisa `environment.ts` tiene la URL correcta
- Busca errores de CORS en F12 → Console

### Login no funciona
- Verifica que la base de datos tiene el usuario
- Confirma que el username y password son correctos
- Busca errores en F12 → Network → log-in request

### Posts no se cargan
- ¿Estás autenticado?
- ¿Hay posts en la base de datos?
- Revisa F12 → Network → /posts request
- Revisa F12 → Console para errores

### Cambios no se ven
```bash
# Reinicia el servidor
# Limpia el cache del navegador (Ctrl+Shift+Del)
# Haz hard refresh (Ctrl+Shift+R)
```

---

**Una vez todo esté ✅, ¡tu frontend está completamente funcional!**
