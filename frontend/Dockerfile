FROM node:14.15.4-alpine AS build

WORKDIR /usr/src/frontend

COPY ./package.json ./package-lock.json ./

RUN npm cache clean --force
RUN npm i

COPY ./ .

RUN npm run build

FROM nginx:1.17.1-alpine

COPY --from=build /usr/src/frontend/dist/frontend /usr/share/nginx/html