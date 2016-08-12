<template>
    <div>
        <div v-if="message" class="message text-center">
            <p class="bg-warning"><small>{{message}}</small></p>
            <a class="btn-default btn configure" v-link="{path: '/'}">Go to settings wizard</a>
        </div>
        <div id="chart" v-show="!message"></div>
    </div>
</template>
<style>
    .message p {
        padding: 5px;
    }
    
    .message small {
        font-size: 80%;
        line-height: 0.5;
    }
    
    .configure {
        font-size: 200%;
        color: #bbb;
        display: inline-block;
    }
</style>
<script>
    import Endpoint from '../../endpoint.js'
    import * as Utils from '../../utils.js'
    import Chart from './Chart.vue'

    export default {
        props: ['testRuns', 'message'],
        data: () => {
            return {
                parameters: Utils.getTestFilters(),
                chartOptions: {
                    width: '100%',
                    chartArea: {
                        top: 5,
                        left: 25,
                        right: 15,
                        bottom: 100
                    },
                    animation: {
                        duration: 1000,
                        easing: 'out',
                        startup: true
                    },
                    height: window.innerHeight,
                    legend: {
                        position: 'bottom',
                        maxLines: 3
                    },
                    hAxis: {
                        slantedText: true,
                        slantedTextAngle: 60,
                        direction: -1,
                        textStyle: {
                            fontSize: 10
                        }
                    },
                    vAxis: {
                        minValue: 0,
                        textStyle: {
                            fontSize: 10
                        }
                    },
                    // colors from dynatrace rich client
                    colors: ['#2ab06f', '#dc172a', '#ffe11c', '#2ab6f4', '#ef651f', '#b7b7b7']
                }
            }
        },
        watch: {
            //redraw if testRuns changes
            testRuns: 'draw'
        },
        created() {
            // start update interval and fetch first result
            this.handleSettings()
        },
        ready() {
            if (this.testRuns) {
                this.draw()
            }
            window.addEventListener('resize', this.handleResize)
        },
        beforeDestroy() {
            clearInterval(this.interval)
            window.removeEventListener('resize', this.handleResize)
        },
        events: {
            'settings-saved': 'handleSettings'
        },
        methods: {
            //handles settings changes
            handleSettings() {
                if (this.interval) {
                    clearInterval(this.interval)
                }
                this.interval = setInterval(this.fetch, Utils.getParameter('update') * 1000)
                this.fetch()
            },
            fetch() {
                Endpoint.fetch('rest/management/profiles/' + Utils.getParameter('profile') + '/testruns.jsonp' +
                        // querystring: we could in theory use built-in superagent api for specyfing query string, but this is easier since we don't convert it to weird objects
                        Utils.convertParametersToQuery(this.parameters))
                    .then((response) => {
                        this.testRuns = response.testRuns
                        this.message = response.message
                    }).catch((err) => {
                        this.testRuns = {}
                        this.message = err
                    })
            },
            handleResize(event) {
                this.chartOptions.height = window.innerHeight,
                    //we do not change width as it's set to 100%
                    this.draw()
            },
        }
    }
</script>