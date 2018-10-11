const path = require('path');
const express = require('express');
const bodyParser = require('body-parser');

const morgan = require('morgan');

const app = express();

const PUBLIC_PATH = path.resolve(__dirname, '..', 'dist');
app.use(morgan('dev'));
app.use(express.static(PUBLIC_PATH));

app.use((req, res) => res.sendFile(path.resolve(PUBLIC_PATH, 'index.html')));
const PORT = 5000;


app.listen(PORT, () => console.info(`Listening on port ${5000}`));
const loki = require('lokijs');

const db = new loki('loki.json')

var shipments = db.addCollection('shipments');

const shipapp = express();

shipapp.use(bodyParser.json());

shipapp.post("/shipment/:id", (req, res) => {
  console.log(`Got shipment from ` + req.params.id);
  console.log(`body: ` + JSON.stringify(req.body, null, 2));
  shipments.insert(req.body);
  res.sendStatus(200);
})

shipapp.get("/shipment/:id", (req, res) => {
  console.log(`id: ` + req.params.id);
  shipment = shipments.findOne({ "id": req.params.id });
  res.end(JSON.stringify(shipment, null, 2));
})

shipapp.listen(5001, () => console.info(`Listening on port ${5001}`));
