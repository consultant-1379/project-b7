FROM node:14.16.0-alpine AS build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
RUN npm install -g @angular/cli@11.2.2
COPY . .
CMD ng serve --host 0.0.0.0
EXPOSE 4200
