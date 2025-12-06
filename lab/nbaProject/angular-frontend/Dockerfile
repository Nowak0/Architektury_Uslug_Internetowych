# Stage 1: Build Angular application
FROM node:18-alpine AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Stage 2: Serve with NGINX
FROM nginx:alpine

# Copy built application
COPY --from=build /app/dist/angular-frontend/browser /usr/share/nginx/html

# Copy NGINX configuration template
COPY nginx.conf.template /etc/nginx/templates/default.conf.template

# Expose port 80
EXPOSE 80

# Use envsubst to replace environment variables in NGINX config
CMD ["/bin/sh", "-c", "envsubst '$$GATEWAY_URL' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
