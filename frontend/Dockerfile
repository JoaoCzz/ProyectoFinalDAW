# Etapa 1: Compilación del Front (Node)
FROM node:20-alpine AS build
WORKDIR /app

# Copiamos los archivos de dependencias
COPY package*.json ./
RUN npm install

# Copiamos el resto del código y compilamos
COPY . .
RUN npm run build -- --configuration=production

# Etapa 2: Servidor web para producción (Nginx)
FROM nginx:1.25-alpine

# Copiamos los archivos compilados al directorio de Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# ¡PASO CLAVE! Copiamos tu configuración antes de arrancar el servidor
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# El comando de arranque siempre se queda al final del todo
CMD ["nginx", "-g", "daemon off;"]
