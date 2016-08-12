<template>
   <fieldset>
        <ul class="list-unstyled list-inline">
            <li v-for="val in parameters" class="form-group">
                <label for="cfg-{{ val.name }}">
                    {{{ val.display ? val.display : beautify(val.name) }}}
                </label>
                <input id="cfg-{{val.name}}" class="form-control" type="{{ val.type ? val.type : 'text' }}" v-model="val.value" name="{{val.name}}">
            </li>
        </ul>
    </fieldset>
</template>
<style>
    form label {
        width: 120px;
        text-align: right;
    }
    
    form li {
        margin: 5px;
    }
</style>
<script>
    export default {
        name: 'SettingsParameters',
        props: ["parameters"],
        methods: {
            beautify(key) {
                //turn camelCase into camel case
                let display = key.replace(/([A-Z])/g, " $1").toLowerCase()
                //capitalise first letter leading to Camel case                
                return display.charAt(0).toUpperCase() + display.slice(1)
            }
        }
    }
</script>