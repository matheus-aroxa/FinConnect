# Estágio 1: Build
FROM node:20-alpine AS build
WORKDIR /app

# Copia os arquivos de dependência
COPY package*.json ./
RUN npm install

# Copia o restante do código e gera o build
COPY . .
RUN npm run build -- --configuration production

# Estágio 2: Serve com Nginx
FROM nginx:alpine

# Copia a configuração customizada do Nginx
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# Copia os arquivos estáticos do build para o diretório do Nginx
# O caminho de saída padrão para novas versões do Angular é dist/frontend/browser
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
