<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
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
        <a-form-item label="番剧名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'masterName', validatorRules.masterName]" placeholder="请输入番剧名称"></a-input>
        </a-form-item>
        <a-form-item label="原始名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sourceName', validatorRules.sourceName]" placeholder="请输入原始名称"></a-input>
        </a-form-item>
        <a-form-item label="索引id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'indexId', validatorRules.indexId]" placeholder="请输入索引id"></a-input>
        </a-form-item>
        <a-form-item label="章节" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'chapter', validatorRules.chapter]" placeholder="请输入章节"></a-input>
        </a-form-item>
        <a-form-item label="大小" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'size', validatorRules.size]" placeholder="请输入大小" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="时长" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'len', validatorRules.len]" placeholder="请输入时长"></a-input>
        </a-form-item>
        <a-form-item label="宽" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'width', validatorRules.width]" placeholder="请输入宽" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="高" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'height', validatorRules.height]" placeholder="请输入高" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="帧率" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'frame', validatorRules.frame]" placeholder="请输入帧率" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="视频流信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'videoStream', validatorRules.videoStream]" placeholder="请输入视频流信息"></a-input>
        </a-form-item>
        <a-form-item label="流数量" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'streamCnt', validatorRules.streamCnt]" placeholder="请输入流数量" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="全路径" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'path', validatorRules.path]" placeholder="请输入全路径"></a-input>
        </a-form-item>
        <a-form-item label="文件名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'fileName', validatorRules.fileName]" placeholder="请输入文件名称"></a-input>
        </a-form-item>
        <a-form-item label="格式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'ext', validatorRules.ext]" placeholder="请输入格式"></a-input>
        </a-form-item>
        <a-form-item label="别名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nameAlise', validatorRules.nameAlise]" placeholder="请输入别名"></a-input>
        </a-form-item>
        <a-form-item label="别名2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nameAlise2', validatorRules.nameAlise2]" placeholder="请输入别名2"></a-input>
        </a-form-item>
        <a-form-item label="父级目录" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'nameParent', validatorRules.nameParent]" placeholder="请输入父级目录"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: "VideoFileModal",
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
            masterName: {rules: [
            ]},
            sourceName: {rules: [
            ]},
            indexId: {rules: [
            ]},
            chapter: {rules: [
            ]},
            size: {rules: [
            ]},
            len: {rules: [
            ]},
            width: {rules: [
            ]},
            height: {rules: [
            ]},
            frame: {rules: [
            ]},
            videoStream: {rules: [
            ]},
            streamCnt: {rules: [
            ]},
            path: {rules: [
            ]},
            fileName: {rules: [
            ]},
            ext: {rules: [
            ]},
            nameAlise: {rules: [
            ]},
            nameAlise2: {rules: [
            ]},
            nameParent: {rules: [
            ]},
},
        url: {
          add: "/mediastream/videoFile/add",
          edit: "/mediastream/videoFile/edit",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','masterName','sourceName','indexId','chapter','size','len','width','height','frame','videoStream','streamCnt','path','fileName','ext','nameAlise','nameAlise2','nameParent'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','masterName','sourceName','indexId','chapter','size','len','width','height','frame','videoStream','streamCnt','path','fileName','ext','nameAlise','nameAlise2','nameParent'))
      },

      
    }
  }
</script>