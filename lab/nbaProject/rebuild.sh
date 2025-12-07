#!/bin/bash

echo "🛑 Zatrzymywanie i usuwanie kontenerów..."
docker-compose down

echo "🗑️  Usuwanie starych obrazów..."
docker-compose rm -f

echo "🔨 Przebudowywanie obrazów..."
docker-compose build --no-cache

echo "🚀 Uruchamianie aplikacji..."
docker-compose up -d

echo "✅ Gotowe! Aplikacja działa."
echo "📊 Sprawdź logi: docker-compose logs -f"
