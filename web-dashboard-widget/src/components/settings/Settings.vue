<template>
    <div class="settings">
        <h1>Settings</h1>
        <form v-on:submit.prevent="updateURL" class="form-inline">
            <settings-parameters :parameters="defaultParameters"></settings-parameters>
            <fieldset>
                <label for="selected-component">Chart type</label>
                <select class="form-control" id="selected-component" v-model="selected">
                    <option v-for="(k,v) in components" value="{{k}}">{{v.name}}</option>
                </select>
            </fieldset>
            <settings-parameters :parameters="components[selected].parameters" :selected="selected"></settings-parameters>
            <fieldset class="text-center">
                <input type="submit" class="btn btn-primary save-settings" value="Generate">
            </fieldset>
        </form>
        <code v-if="url"><a href="{{url}}">{{ url }}</a></code>
    </div>
</template>
<style>
    code {
        display: inline-block;
        padding: 20px;
        text-align: center;
        width: 100%;
        margin: 10px 0;
    }
</style>
<script>
    import SettingsParameters from './SettingsParameters.vue'
    import * as Utils from '../../utils.js'

    export default {
        name: 'Settings',
        components: {
            settingsParameters: SettingsParameters
        },
        data: () => {
            return {
                url: '',
                selected: 1,
                defaultParameters: [{
                    name: 'protocol',
                    value: 'https'
                }, {
                    name: 'host',
                    value: 'localhost'
                }, {
                    name: 'port',
                    type: 'number',
                    value: 8021,
                }, {
                    name: 'username',
                    value: 'admin'
                }, {
                    name: 'password',
                    type: 'password',
                    value: 'admin'
                }, {
                    name: 'profile',
                    value: 'Monitoring'
                }, {
                    name: 'update',
                    display: 'Update every (s)',
                    type: 'number',
                    value: '30'
                }],
                components: [{
                    name: 'Pie Chart',
                    internal: 'pie',
                    parameters: Utils.getTestFilters()
                }, {
                    name: 'Column Chart',
                    internal: 'column',
                    parameters: Utils.getTestFilters()
                }],
            }
        },
        props: ["parameters"],
        methods: {
            // updates the URL for display
            updateURL() {
                // take the current url and remove the anchor part of it
                let addr = window.location.href.replace(window.location.hash, '')
                // check which chart type was selected
                let selectedComponent = this.components[this.selected]
                    // add the task type internal name as a hash to display correct component (vue things)
                addr += '#!/' + selectedComponent.internal + '?'
                    // iterate over both default parameters (applicable to all chart types) and chart specific parameters and put them in query string
                for (let param of this.defaultParameters.concat(selectedComponent.parameters)) {
                    if (param.value) {
                        addr += param.name + '=' + encodeURIComponent(param.value) + '&'
                    }
                }
                this.url = addr
            }
        }
    }
</script>