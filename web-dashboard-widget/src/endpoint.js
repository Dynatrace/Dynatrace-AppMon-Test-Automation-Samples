import {getParameter} from './utils.js'

const request = require('superagent')
const jsonp = require('superagent-jsonp')

const TIMEOUT = 60000

export default class {
    constructor(settings) {
        this.settings = settings
    }

    static buildURL(endpoint) {
        // schema://username:password@host:port/endpoint
        return getParameter('protocol') + '://' + getParameter('username') + ':' + getParameter('password') + '@' + getParameter('host') + ':' + getParameter('port') + '/' + endpoint
    }

    static fetch(endpoint) {
        return new Promise((resolve, reject) => {
            // use jsonp to work-around CORS issues
            request.get(this.buildURL(endpoint)).use(jsonp({ callbackName: 'callback', timeout: TIMEOUT })).timeout(TIMEOUT).end((err, res) => {
                if (err) {
                    reject(err)
                    return
                }
                if (res.status < 200 || res.status >= 300) {
                    //there's an error message sent, but stupidly under different callback and thus, we can't retrieve it
                    reject("Invalid status code: " + res.status)
                    return
                }
                resolve(res.body)
            })
        })
    }
}