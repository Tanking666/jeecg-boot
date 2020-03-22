<template>
  <div>
    <div v-show='!isError'>
      <video id="videobox" class="video-js vjs-default-skin vjs-big-play-centered vjs-16-9" controls preload="auto" webkit-playsinline="true"
             playsinline="true" type="application/x-mpegURL" allowsInlineMediaPlayback=YES webview.allowsInlineMediaPlayback=YES width='100%'
             ref='videoRef' x5-video-player-fullscreen="true" :poster="posterSrc">
<!--        <source id="sourceBox" :src="videoSrc">-->
        <source id="sourceBox" src="1.mp4">
        <p class="vjs-no-js">不支持播放</p>
      </video>
    </div>
    <div v-show='isError' class="errorTip"><p>视频出错了！</p></div>
  </div>
</template>
<style>

</style>
<script>
    import videojs from 'video.js'
    import 'videojs-contrib-hls'
    import 'video.js/dist/video-js.css'
    
    export default {
        name: 'VideoLayout',
        data () {
            return {
                videoSrc: './1.mp4',
                posterSrc: 'https://matrimony001.100msh.net.cn/public/code/material/mp-7261-1554175849.jpg',
                isError: false
            }
        },
        mounted () {
            //为避免在初始化video时播放源是空的，报播放源错误，需要先给source 的src赋值
            var player = videojs('videobox', {
                    bigPlayButton: true,
                    textTrackDisplay: true,
                    posterImage: true,
                    errorDisplay: false,
                    controlBar: false,
                    playbackRates: [0.5, 1, 1.5, 2],
                    ControlBar: {
                        customControlSpacer: true
                    }
                },
                function onPlayerReady () {
                    this.play()
                    setTimeout(() => {   //延时确保能监听到视频源错误
                        var mediaError = this.error()
                        if (mediaError != null && mediaError.code) {
                            _this.isError = true
                            Dialog.alert({
                                message: '啊哦，播放出错了。<br>请刷新重试，如无法播放建议您观看其它内容。',
                                confirmButtonText: '确定'
                            }).then(() => {
                                _this.goback()
                            })
                        }
                    }, 1000)
                })
            // player.width(this.videoW)   //设置播放器宽度
        },
        beforeDestroy () {
            const videoDom = this.$refs.videoRef   //不能用document 获取节点
            videojs(videoDom).dispose()  //销毁video实例，避免出现节点不存在 但是flash一直在执行,也避免重新进入页面video未重新声明
        }
    }
</script>
<style scoped>

</style>