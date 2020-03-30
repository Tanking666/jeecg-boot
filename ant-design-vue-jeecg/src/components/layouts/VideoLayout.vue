<template>
  <div>
    <video id="myVideo" class="video-js" controls="controls">
      <source src="http://kxyu.top:3001/jeecg-boot/resource/1.mp4" type="video/mp4">
      <track src="http://kxyu.top:3001/jeecg-boot/resource/1.vtt" srclang="zh" label="简体中文" kind="subtitles" />

    </video>
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

            }
        },
        mounted () {
            this.initVideo()
        },
        methods: {
            initVideo () {
                //初始化视频方法
                let myPlayer = videojs('myVideo', {
                    //确定播放器是否具有用户可以与之交互的控件。没有控件，启动视频播放的唯一方法是使用autoplay属性或通过Player API。
                    controls: true,
                    //自动播放属性,muted:静音播放
                    autoplay: false,
                    //建议浏览器是否应在<video>加载元素后立即开始下载视频数据。
                    preload: 'auto',
                    //设置视频播放器的显示宽度（以像素为单位）
                    width: '1280px',
                    //设置视频播放器的显示高度（以像素为单位）
                    height: '720px'
                });
            }

        }
    }
</script>
<style scoped>

</style>