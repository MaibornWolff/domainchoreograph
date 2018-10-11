FROM node:9

# Create directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Install dependencies
COPY package.json /usr/src/app
COPY yarn.lock /usr/src/app
RUN yarn install

# COPY Files
COPY . /usr/src/app

# Lint and build
RUN yarn lint
RUN yarn build

# Run
RUN yarn global add pm2
ENV NODE_ENV production
EXPOSE 5000
RUN chmod +x ./bin/http.js
CMD ["pm2-docker", "./bin/http.js"]
