<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入更新人"></a-input>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新日期" v-decorator="[ 'updateTime', validatorRules.updateTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="年代" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'name', validatorRules.name]" placeholder="请输入年代"></a-input>
        </a-form-item>
        <a-form-item label="季度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'season', validatorRules.season]" placeholder="请输入季度"></a-input>
        </a-form-item>
        <a-form-item label="总集数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'allChapterNum', validatorRules.allChapterNum]" placeholder="请输入总集数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="包含视频数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'fileCnt', validatorRules.fileCnt]" placeholder="请输入包含视频数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="年代" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'year', validatorRules.year]" placeholder="请输入年代"></a-input>
        </a-form-item>
        <a-form-item label="初版名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sourName', validatorRules.sourName]" placeholder="请输入初版名称"></a-input>
        </a-form-item>
        <a-form-item label="标签" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tag', validatorRules.tag]" placeholder="请输入标签"></a-input>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "VideoIndexModal",
    components: { 
      JDate,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
validatorRules: {
            createBy: {rules: [
            ]},
            createTime: {rules: [
            ]},
            updateBy: {rules: [
            ]},
            updateTime: {rules: [
            ]},
            name: {rules: [
            ]},
            season: {rules: [
            ]},
            allChapterNum: {rules: [
            ]},
            fileCnt: {rules: [
            ]},
            year: {rules: [
            ]},
            sourName: {rules: [
            ]},
            tag: {rules: [
            ]},
},
        url: {
          add: "/mediastream/videoIndex/add",
          edit: "/mediastream/videoIndex/edit",
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','name','season','allChapterNum','fileCnt','year','sourName','sourId','tag'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','name','season','allChapterNum','fileCnt','year','sourName','sourId','tag'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>