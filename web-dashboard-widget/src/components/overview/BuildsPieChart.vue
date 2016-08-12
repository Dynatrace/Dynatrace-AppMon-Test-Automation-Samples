<script>
    import Chart from './Chart.vue'
    export default {
        name: 'BuildsPieChart',
        mixins: [Chart],
        ready() {
            this.chart = new google.visualization.PieChart(document.getElementById("chart"))
        },
        methods: {
            draw() {
                let data = [
                    ["Status", "Count"],
                    ["Passed", 0],
                    ["Failed", 0],
                    ["Volatile", 0],
                    ["Improved", 0],
                    ["Degraded", 0],
                    ["Invalidated", 0]
                ];

                for (let testRun of this.testRuns) {
                    data[1][1] += testRun.numPassed
                    data[2][1] += testRun.numFailed
                    data[3][1] += testRun.numVolatile
                    data[4][1] += testRun.numImproved
                    data[5][1] += testRun.numDegraded
                    data[6][1] += testRun.numInvalidated
                }

                let tableData = google.visualization.arrayToDataTable(data)

                // Set chart options
                this.chartOptions.chartArea.bottom = 50
                this.chartOptions.chartArea.top = 0

                this.chart.draw(tableData, this.chartOptions)
            }
        }
    }
</script>