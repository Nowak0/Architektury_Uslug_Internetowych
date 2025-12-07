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

# Install gettext for envsubst and bash for scripting
RUN apk add --no-cache gettext bash

# Copy built application from build stage
COPY --from=build /app/dist/angular-frontend /tmp/angular-dist

# Intelligently copy files (handles both browser subdir and direct output)
RUN if [ -d "/tmp/angular-dist/browser" ]; then \
      cp -r /tmp/angular-dist/browser/* /usr/share/nginx/html/; \
    else \
      cp -r /tmp/angular-dist/* /usr/share/nginx/html/; \
    fi && \
    rm -rf /tmp/angular-dist

# Copy nginx template
COPY nginx.conf.template /etc/nginx/templates/default.conf.template

# Environment variable for Gateway URL
ENV GATEWAY_URL=http://gateway:8080

# Expose port
EXPOSE 80

# Use envsubst to process template and start nginx
CMD ["/bin/sh", "-c", "envsubst '$$GATEWAY_URL' < /etc/nginx/templates/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
