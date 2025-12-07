# Stage 1: Build Angular application
FROM node:20-alpine AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the application in production mode
RUN npm run build

# Stage 2: Serve with NGINX
FROM nginx:alpine

# Install gettext for envsubst
RUN apk add --no-cache gettext

# Copy built application from build stage
COPY --from=build /app/dist/angular-frontend/browser /usr/share/nginx/html

# Copy nginx template
COPY nginx.conf.template /etc/nginx/templates/default.conf.template

# Environment variable for Gateway URL
ENV GATEWAY_URL=http://gateway:8080

# Expose port
EXPOSE 80

# Use envsubst to process template and start nginx
CMD ["/bin/sh", "-c", "envsubst '$$GATEWAY_URL' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
