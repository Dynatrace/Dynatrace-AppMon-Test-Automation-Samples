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
        <code><a href="{{url}}">{{ url }}</a></code>
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
    import {
        getTestFilters,
        parametersToQuery
    } from '../../utils.js'

    export default {
        name: 'Settings',
        components: {
            settingsParameters: SettingsParameters
        },
        data: () => {
            return {
                url: '',
                selected: 0,
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
                    display: 'Update (s)',
                    type: 'number',
                    value: '30'
                }],
                components: [{
                    name: 'Pie Chart',
                    internal: 'pie',
                    parameters: getTestFilters()
                }, {
                    name: 'Column Chart',
                    internal: 'column',
                    parameters: getTestFilters()
                }],
            }
        },
        props: ["parameters"],
        methods: {
            updateURL() {
                let addr = window.location.href.replace(window.location.hash, '');
                let selectedComponent = this.components[this.selected]
                addr += '#!/'+selectedComponent.internal + '?'
                for (let param of this.defaultParameters.concat(selectedComponent.parameters)) {
                    if (param.value) {
                        addr += param.name + '=' + param.value + '&'
                    }
                }
                this.url = addr
            }
        }
    }
</script>