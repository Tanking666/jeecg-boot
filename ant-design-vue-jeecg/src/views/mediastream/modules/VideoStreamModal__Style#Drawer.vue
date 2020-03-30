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
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sysOrgCode', validatorRules.sysOrgCode]" placeholder="请输入所属部门"></a-input>
        </a-form-item>
        <a-form-item label="视频文件ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'videoFileId', validatorRules.videoFileId]" placeholder="请输入视频文件ID"></a-input>
        </a-form-item>
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'type', validatorRules.type]" placeholder="请输入类型"></a-input>
        </a-form-item>
        <a-form-item label="顺序" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'no', validatorRules.no]" placeholder="请输入顺序" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="编码信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'info', validatorRules.info]" placeholder="请输入编码信息"></a-input>
        </a-form-item>
        <a-form-item label="语言" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'lang', validatorRules.lang]" placeholder="请输入语言"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
        </a-form-item>
        <a-form-item label="默认标志" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'def', validatorRules.def]" placeholder="请输入默认标志" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="扩展名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ext', validatorRules.ext]" placeholder="请输入扩展名"></a-input>
        </a-form-item>
        <a-form-item label="路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['path', validatorRules.path]" rows="4" placeholder="请输入路径"/>
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
    name: "VideoStreamModal",
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
            sysOrgCode: {rules: [
            ]},
            videoFileId: {rules: [
            ]},
            type: {rules: [
            ]},
            no: {rules: [
            ]},
            info: {rules: [
            ]},
            lang: {rules: [
            ]},
            remark: {rules: [
            ]},
            def: {rules: [
            ]},
            ext: {rules: [
            ]},
            path: {rules: [
            ]},
},
        url: {
          add: "/mediastream/videoStream/add",
          edit: "/mediastream/videoStream/edit",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','videoFileId','type','no','info','lang','remark','def','ext','path'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','videoFileId','type','no','info','lang','remark','def','ext','path'))
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