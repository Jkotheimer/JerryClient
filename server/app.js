const express = require('express');
const cors = require('cors')
const app = express();

const public = `${__dirname}/public`

app.use(express.json())
app.use(cors())

app.get('/', function(req, res) {
    res.sendFile(`${public}/index.html`)
})

app.post('/', function(req, res) {
    console.log('Got a request!')
    console.log(typeof req.body)
    console.log(req.body)
    res.setHeader('Access-Control-Allow-Origin', '*')
    res.setHeader('Content-Type', 'application/json')
    res.statusCode = 200
    res.json(req.body)
});

app.listen(3000);