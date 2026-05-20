FROM node:alpine

WORKDIR /usr/app
COPY ./ /usr/app
RUN npm install mongodb

CMD [node, /usr/app/db-bootstrap.js]