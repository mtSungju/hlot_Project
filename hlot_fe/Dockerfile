# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
COPY nginx.conf /etc/nginx/nginx.conf
ADD start.sh /
RUN chmod +x /start.sh
CMD ["/start.sh"]
