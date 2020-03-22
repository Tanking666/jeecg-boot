<template>
  <div id="userLayout" :class="['user-layout-wrapper', device]">
    <div class="container">
        <div class="top">
          <div class="header">
            <a href="/">
              <img src="~@/assets/logo.svg" class="logo" alt="logo">
              <span class="title">这是标题</span>
            </a>
          </div>
          <div class="desc">
            这是个副标题
          </div>
        </div>

        <route-view>

        </route-view>

        <div class="footer">
          <div class="links">
            <a href="https://www.bilibili.com/" target="_blank">B站</a>
            <a href="http://konachan.wjcodes.com/index.php?tag=realistic" target="_blank">K站</a>
            <a href="https://www.baidu.com/" target="_blank">百度</a>
          </div>
          <div class="copyright">
            Copyright &copy; 2099 <a href="http://kxyu.top" target="_blank">XXXX</a> 出品
          </div>
        </div>
      </div>
    </div>
</template>

<script>
    import RouteView from '@/components/layouts/RouteView'
    import { mixinDevice } from '@/utils/mixin.js'

    export default {
        name: 'UserLayout',
        components: { RouteView },
        mixins: [mixinDevice],
        data () {
            return {}
        },
        mounted () {
            document.body.classList.add('userLayout')
        },
        beforeDestroy () {
            document.body.classList.remove('userLayout')
        }
    }
</script>

<style lang="less" scoped>
  #userLayout.user-layout-wrapper {
    height: 100%;

    &.mobile {
      .container {
        background: #f0f2f5 url(~@/assets/background2.jpg) no-repeat 50%;
        position: fixed;
        .main {
          max-width: 70%;
          width: 98%;
          margin: 20% auto 0 auto;
          border-radius: 5px;
        }
      }
    }

    .container {
      width: 100%;
      min-height: 100%;
      background: #f0f2f5 url(~@/assets/background2.jpg) no-repeat 50%;
      background-size: 100%;
      padding: 110px 0 144px;
      position: relative;
      /*opacity:80%;*/



      a {
        text-decoration: none;
      }

      .top {
        text-align: center;

        .header {
          height: 44px;
          line-height: 44px;

          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }

          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }

          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: "Chinese Quote", -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }

        .desc {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.45);
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }

      .main {
        padding: 50px;
        min-width: 260px;
        width: 468px;
        margin: auto 0 auto 60%;
        background-color: rgba(255,255,255,0.7);
        border-radius: 10px;

      }

      .footer {
        position: absolute;
        width: 100%;
        bottom: 0;
        padding: 0 16px;
        margin: 48px 0 24px;
        text-align: center;

        .links {
          margin-bottom: 8px;
          font-size: 14px;

          a {
            color: rgba(0, 0, 0, 0.45);
            transition: all 0.3s;

            &:not(:last-child) {
              margin-right: 40px;
            }
          }
        }

        .copyright {
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
        }
      }
    }
  }
</style>