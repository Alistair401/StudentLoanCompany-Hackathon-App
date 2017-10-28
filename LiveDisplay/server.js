var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var request = require('request');

var router = express.Router();

app.use(bodyParser.json());

router.use(function (req, res, next) {
    next();
})

router.route('/').get(function (req, res) {
    let result = "<html>"
    request({
        url: 'http://default-environment.xnjdwwknda.us-west-2.elasticbeanstalk.com/retrieve',
        method: 'GET',
    }, function (error, response, body) {
        let data = JSON.parse(body);
        data.forEach((entry) => {
            Object.keys(entry).forEach((key) => {
                result += "<p>"
                if (key == "image" || key == "signature") {
                    result += base64ToImage(entry[key]);
                } else {
                    result += entry[key];
                }
                result += "</p>"
            })
        });
        result += "</html>"
        res.send(result)
    });
})

function base64ToImage(b64) {
    let result = '<img src="data:image/png;base64, '
    result += b64;
    result+= '"/>'
    return result;
}

app.use('/', router);

app.listen(80);
