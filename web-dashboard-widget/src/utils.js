const TEST_FILTERS = [
    {
        name: 'startTime',
        display: 'Since when in ms',
        type: 'number'
    }, {
        name: 'endTime',
        display: 'Until when in ms',
        type: 'number'
    }, {
        name: 'lastNBuilds',
        type: 'number',
        display: 'Last <var>n</var> builds',
        // default value in settings, not taken into consideration in charts view
        value: 10
    }, {
        name: 'lastNTestruns',
        display: 'Last <var>n</var> test runs',
        type: 'number'
    }, {
        name: 'versionBuild',
    }, {
        name: 'versionMajor'
    }, {
        name: 'versionMilestone'
    }, {
        name: 'versionMinor'
    }, {
        name: 'versionRevision'
    }, {
        name: 'category'
    }, {
        name: 'agentGroup'
    }, {
        name: 'markerName'
    }, {
        name: 'platform'
    }
]

/**
 * Gets list of supported filters for test automation endpoints
 */
export function getTestFilters() {
    // pretend this didn't happen
    // deep-cloning trickery
    return JSON.parse(JSON.stringify(TEST_FILTERS))
}

/**
 * Converts an array of parameters into query string
 * parameter has to be in {name: 'name', def: 'defaultValue'} format
 * parameter value is fetched from query string using getParameter
 */
export function convertParametersToQuery(parameters) {
    let params = '?'
    for (let param of parameters) {
        let val = this.getParameter(param.name) ? this.getParameter(param.name) : param.def
        if (val) {
            params += param.name + '=' + encodeURIComponent(val) + '&'
        }
    }
    return params
}

/**
 * Fetches a single parameter from query string
 */
export function getParameter(name) {
    let match = new RegExp('[?&]' + name + '=([^&]*)').exec(window.location)
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '))
}