const TEST_FILTERS = [
    {
        name: 'startTime',
        type: 'number'
    }, {
        name: 'endTime',
        type: 'number'
    }, {
        name: 'lastNBuilds',
        type: 'number',
        value: 10
    }, {
        name: 'lastNTestruns',
        type: 'number'
    }, {
        name: 'versionBuild'
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

export function getTestFilters() {
    // pretend this didn't happen
    // deep-cloning trickery
    return JSON.parse(JSON.stringify(TEST_FILTERS))
}

export function convertParametersToQuery(parameters) {
    let params = '?'
    for (let param of parameters) {
        let val = this.getParameter(param.name) ? this.getParameter(param.name) : param.def;
        if (val) {
            params += param.name + '=' + val + '&'
        }
    }
    return params
}

export function getParameter(name) {
    let match = new RegExp('[?&]' + name + '=([^&]*)').exec(window.location)
    return match && decodeURIComponent(match[1].replace(/\+/g, ' '))
}