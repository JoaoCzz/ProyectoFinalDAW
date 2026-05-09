# AWS EC2 Deploy

## Objetivo

Desplegar todo en una sola instancia EC2 usando tu repositorio de GitHub:

- Angular compilado servido por Nginx
- Spring Boot como servicio `systemd`
- MySQL en la misma máquina
- actualizaciones con `git pull` y un script único de deploy

## Arquitectura

- Frontend: `http://tu-dominio/`
- API: `http://tu-dominio/api/...`
- Nginx reenvía `/api` al backend en `127.0.0.1:8080`
- MySQL local en la EC2

El frontend ya queda preparado para producción con `apiUrl: '/api'`.

## 1. Crear la instancia

- AMI: Ubuntu 24.04 LTS o Ubuntu 22.04 LTS
- Tipo: `t3.small` o `t3.micro` para demo pequeña
- Security Group:
  - `22` SSH desde tu IP
  - `80` HTTP desde `0.0.0.0/0`
  - `443` HTTPS desde `0.0.0.0/0`

## 2. Instalar dependencias

```bash
sudo apt update
sudo apt install -y openjdk-17-jre nginx mysql-server certbot python3-certbot-nginx
```

## 3. Preparar carpetas

```bash
sudo mkdir -p /opt/proyectofinal/backend
sudo mkdir -p /opt/proyectofinal/app
sudo mkdir -p /var/www/proyectofinal
sudo chown -R ubuntu:ubuntu /opt/proyectofinal
sudo chown -R ubuntu:ubuntu /var/www/proyectofinal
```

## 4. Clonar el repositorio

En la EC2:

```bash
cd /opt/proyectofinal
git clone https://github.com/TU_USUARIO/TU_REPO.git app
cd app
```

Si tu repo es privado, usa una clave SSH de despliegue o un token.

## 5. Base de datos MySQL

Entrar en MySQL:

```bash
sudo mysql
```

Crear base y usuario:

```sqlcd
CREATE DATABASE ProyectoTFG CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'tu_password_segura';
GRANT ALL PRIVILEGES ON ProyectoTFG.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

Si quieres importar tu base local:

```bash
mysqldump -u root -p ProyectoTFG > dump.sql
mysql -u appuser -p ProyectoTFG < dump.sql
```

## 6. Backend

Instala el archivo de ejemplo para variables:

```bash
cp /opt/proyectofinal/app/deploy/ec2/backend.env.example /opt/proyectofinal/backend/.env
```

Edita `/opt/proyectofinal/backend/.env`:

```env
PORT=8080
DATABASE_URL=jdbc:mysql://localhost:3306/ProyectoTFG?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DATABASE_USERNAME=appuser
DATABASE_PASSWORD=tu_password_segura
JWT_PRIVATE_KEY=una_clave_muy_larga_y_aleatoria
JWT_USER_GENERATOR=DUZZ-BACKEND
JPA_DDL_AUTO=update
JPA_SHOW_SQL=false
CORS_ALLOWED_ORIGINS=http://tu-dominio.com,https://tu-dominio.com
```

Instala el servicio:

```bash
sudo cp /opt/proyectofinal/app/deploy/ec2/proyectofinal-backend.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl enable proyectofinal-backend
```

Ver logs:

```bash
journalctl -u proyectofinal-backend -f
```

## 7. Frontend y primer despliegue

Haz ejecutable el script:

```bash
chmod +x /opt/proyectofinal/app/deploy/ec2/deploy.sh
```

Lanza el primer despliegue:

```bash
/opt/proyectofinal/app/deploy/ec2/deploy.sh
```

## 8. Nginx

Instala la configuración:

```bash
sudo cp /opt/proyectofinal/app/deploy/ec2/nginx-proyectofinal.conf /etc/nginx/sites-available/proyectofinal
sudo ln -s /etc/nginx/sites-available/proyectofinal /etc/nginx/sites-enabled/proyectofinal
sudo rm -f /etc/nginx/sites-enabled/default
sudo nginx -t
sudo systemctl reload nginx
```

## 9. HTTPS

Si ya tienes dominio apuntando a la IP pública de la EC2:

```bash
sudo certbot --nginx -d tu-dominio.com -d www.tu-dominio.com
```

## 10. Actualizar despliegue

El flujo normal a partir de aquí es:

```bash
cd /opt/proyectofinal/app
git pull origin main
./deploy/ec2/deploy.sh
```

Si quieres desplegar otra rama:

```bash
./deploy/ec2/deploy.sh nombre-de-rama
```

## 11. Comprobaciones rápidas

- `http://IP_PUBLICA/`
- `http://IP_PUBLICA/api/posts`
- `sudo systemctl status proyectofinal-backend`
- `sudo nginx -t`

## Notas

- Para una demo o TFG, una sola EC2 te vale perfectamente.
- Para algo más serio, lo normal sería separar la base en RDS.
- Si no usas dominio todavía, puedes dejar `CORS_ALLOWED_ORIGINS` con tu IP pública mientras pruebas.
- El script de despliegue usa `npm ci`, así que conviene mantener `package-lock.json` actualizado en GitHub.
