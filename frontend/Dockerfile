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

# Añadimos /browser al final de la ruta
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Copiamos la configuración de Nginx proxy
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# El comando de arranque siempre se queda al final del todo
CMD ["nginx", "-g", "daemon off;"]
