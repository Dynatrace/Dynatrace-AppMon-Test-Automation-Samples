import {getParameter} from './utils.js'

const request = require('superagent')
const jsonp = require('superagent-jsonp')
const TIMEOUT = 60000

export default class {
    constructor(settings) {
        this.settings = settings
    }

    static buildURL(endpoint) {
        return getParameter('protocol') + '://' + getParameter('username') + ':' + getParameter('password') + '@' + getParameter('host') + ':' + getParameter('port') + '/' + endpoint
    }

    static fetch(endpoint) {
        return new Promise((resolve, reject) => {
            request.get(this.buildURL(endpoint)).use(jsonp({ callbackName: 'callback', timeout: TIMEOUT })).timeout(TIMEOUT).end((err, res) => {
                if (err) {
                    reject(err)
                    return
                }
                if (res.status < 200 || res.status >= 300) {
                    reject("Invalid status code: " + res.status)
                    return
                }
                resolve(res.body)
            });
        });
    }
}