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
# Nota: Revisa si tu framework compila en 'dist/tu-app-name' o solo 'dist'
COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
