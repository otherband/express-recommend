FROM node:20.8.0-alpine3.18 as builder

WORKDIR /build

COPY frontend/package.json frontend/package-lock.json .

RUN npm install

COPY frontend/ .

RUN npm run build

FROM nginx:1.25.2-alpine

# TODO: Set build directory location
COPY --from=builder /build/out /usr/share/nginx/html