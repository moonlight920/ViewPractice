<template>
  <div>
    <h4 style="padding-bottom: 16px">{{ mConfig.desc }}</h4>
    <div v-for="(inputItem, k) in mInputList" :key="k">
      <div>
        <p>{{ inputItem.desc }}</p>
      </div>
      <div id="hdt-demo-0" style="margin-bottom: 16px">
        <span>示例：</span>
        <button
          v-for="(demoItem, k) in inputItem.demos"
          :key="k"
          v-on:click="demoClick(inputItem.key, demoItem)"
          class="btn btn-default"
          style="margin-right: 8px"
        >
          {{ demoItem.name }}
        </button>
      </div>
      <div>
        <textarea
          v-bind:id="'input-' + inputItem.key"
          style="width: 100%; height: 200px"
          placeholder="Write something..."
        ></textarea>
      </div>
      <div></div>
    </div>
    <button class="btn btn-info" v-on:click="send">发送</button>
  </div>
</template>

<script>
    module.exports = {
        props: ["config"],
        data: function () {
            return {
                mConfig: this.config.config,
                mInputList: this.config.config.inputList,
                mPid: this.config.pid
            }
        },
        methods: {
            demoClick(key, demo) {
                // 点击demo按钮，获取数据设置给文本框
                if (demo.type === 1) {
                    // 该数据需要请求path
                    let path = demo.data
                    $.ajax({
                        url: path,
                        type: 'GET',
                        dataType: 'text',
                        success: function (result) {
                            $('#input-' + key).text(result)
                        }
                    });
                } else if (demo.type === 2) {
                    // 直接显示该数据
                    $('#input-' + key).text(demo.data)
                }
            },
            send() {
                const postData = {pid: this.mPid};

                for (let index = 0; index < this.mInputList.length; index++) {
                    let input = this.mInputList[index]
                    // 根据id，查找文本框中的文本
                    postData[input.key] = $('#input-' + input.key).val()
                }
                $.ajax({
                    url: "api/submit",
                    type: 'POST',
                    dataType: 'json',
                    data: postData,
                    success: function (response) {
                        if (response.isSuccessful) {
                            showSuccessInfo('发送成功')
                        } else {
                            showErrorInfo(response.error)
                        }
                    }
                })
            }
        },
        mounted() {
            console.log(typeof this.mConfig)
        }
    }
</script>

<style>
</style>
