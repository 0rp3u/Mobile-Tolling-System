'use strict'
const express = require('express')
const db = require('./utils/db/setup.js')

const app = express();
const port = process.env.PORT || 3002;

app.get('/test/:id', async (req, res, next) => {
  
})