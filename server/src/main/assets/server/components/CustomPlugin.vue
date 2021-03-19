<template>
    <div>
        <p>hello custom</p>
        <p>config:</p>
        <p>{{mConfig}}</p>
    </div>
</template>

<script>
    module.exports = {
        props: ["config"],
        data: function () {
            return {
                mConfig: this.config
            }
        }
    }
</script>

<style>
</style>
