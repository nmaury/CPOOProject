#docker run -p 4200:4200 city-builder-frontend
FROM node:14.15.4 as build-stage
WORKDIR /app
#COPY package*.json ./
COPY ./ .
RUN npm install
RUN npm run build
FROM nginx:alpine
RUN mkdir /app
COPY --from=build-stage /app/dist/game-frontend/ /app
COPY nginx.conf /etc/nginx/nginx.conf
USER nginx
EXPOSE 4200