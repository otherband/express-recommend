FROM node:20.8.0-alpine3.18 as builder

WORKDIR /build

COPY frontend/package.json frontend/package-lock.json .

RUN npm install

COPY frontend/ .

RUN npm run build


FROM nginx:1.25.2-alpine

RUN mkdir content

COPY --from=builder /build/js-dist /content/js-dist

COPY frontend/server/nginx.conf /etc/nginx/nginx.conf

COPY frontend/static/ /content/static
