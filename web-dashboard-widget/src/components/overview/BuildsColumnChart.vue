<script>
    import Chart from './Chart.vue'

    export default {
        name: 'BuildsColumnChart',
        props: ['testRuns'],
        mixins: [Chart],
        ready() {
            this.chart = new google.visualization.ColumnChart(document.getElementById("chart"));
        },
        methods: {
            formatVersion(testRun) {
                let verToStr = (ver) => {
                    return ver !== undefined ? ver : '-'
                }
                return verToStr(testRun.versionMajor) + '.' + verToStr(testRun.versionMinor) + '.' + verToStr(testRun.versionRevision) + '.' + verToStr(testRun.versionBuild)
            },
            convertTestRuns() {
                let data = []
                for (let testRun of this.testRuns) {
                    data.push([this.formatVersion(testRun), testRun.numPassed, testRun.numFailed, testRun.numVolatile, testRun.numImproved, testRun.numDegraded, testRun.numInvalidated])
                }
                return data;
            },
            draw() {
                let data = new google.visualization.DataTable()
                data.addColumn('string', 'Name')
                data.addColumn('number', 'Passed')
                data.addColumn('number', 'Failed')
                data.addColumn('number', 'Volatile')
                data.addColumn('number', 'Improved')
                data.addColumn('number', 'Degraded')
                data.addColumn('number', 'Invalidated')
                data.addRows(this.convertTestRuns())

                // Set chart options
                let options = {}
                Object.assign(options, this.chartOptions, {
                    isStacked: true,
                    bar: {
                        groupWidth: '90%'
                    }
                });
                this.chart.draw(data, options)
            }
        }
    }
</script>