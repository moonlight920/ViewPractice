<template>
  <div>
    <h4 style="padding-bottom: 16px">{{ mConfig.desc }}</h4>
    <div id="pull-options" style="margin-bottom: 16px">
      <span>点击获取数据：</span>
      <button
        v-for="(pullItem, k) in mConfig.pullList"
        :key="k"
        v-on:click="getDataFromApp(pullItem.key)"
        class="btn btn-default"
        style="margin-right: 8px"
      >
        {{ pullItem.name }}
      </button>
    </div>
    <div>
      <textarea
        style="width: 100%; height: 500px"
        placeholder="Show something..."
        v-model="mShowData"
      ></textarea>
    </div>
  </div>
</template>

<script>
    module.exports = {
        props: ["config"],
        data: function () {
            return {
                mPid: this.config.pid,
                mConfig: this.config.config,
                mShowData: ''
            }
        },
        mounted() {

        },
        methods: {
            getDataFromApp(key) {
                let that = this
                $.ajax({
                    url: 'api/pull',
                    type: 'GET',
                    dataType: 'json',
                    data: {pid: this.mPid, key: key},
                    success: function (result) {
                        console.log(result)
                        if (result.isSuccessful) {
                            that.mShowData = result.data
                        } else {
                            showErrorInfo(result.error)
                        }
                    }
                });
            }
        }
    }
</script>

<style>
</style>
