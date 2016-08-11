import Vue from 'vue'
import Router from 'vue-router'
import App from './components/App.vue'
import BuildsColumnChart from './components/overview/BuildsColumnChart.vue'
import BuildsPieChart from './components/overview/BuildsPieChart.vue'
import Settings from './components/settings/Settings.vue'

Vue.use(Router)

let router = new Router()

router.map({
    '/column': {
        component: BuildsColumnChart
    },
    '/pie': {
        component: BuildsPieChart
    },
    '/': {
        component: Settings
    }
})

router.beforeEach(() => {
    window.scrollTo(0, 0)
})

console.log(window.location.toString());

google.charts.load('current', {
    'packages': ['corechart']
})

google.charts.setOnLoadCallback(() => {
    router.start(App, '#app')
})