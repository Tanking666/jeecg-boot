package org.jeecg.modules.buss.mediastream.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: tb_video_file
 * @Author: jeecg-boot
 * @Date:   2020-03-25
 * @Version: V1.0
 */
@Data
@TableName("tb_video_file")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_video_file对象", description="tb_video_file")
public class VideoFile implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
    private String id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@Excel(name = "更新日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**番剧名称*/
	@Excel(name = "番剧名称", width = 15)
    @ApiModelProperty(value = "番剧名称")
    private String masterName;
	/**原始名称*/
	@Excel(name = "原始名称", width = 15)
    @ApiModelProperty(value = "原始名称")
    private String sourceName;
	/**索引id*/
	@Excel(name = "索引id", width = 15)
    @ApiModelProperty(value = "索引id")
    private String indexId;
	/**章节*/
	@Excel(name = "章节", width = 15)
    @ApiModelProperty(value = "章节")
    private String chapter;
	/**大小*/
	@Excel(name = "大小", width = 15)
    @ApiModelProperty(value = "大小")
    private Integer size;
	/**时长*/
	@Excel(name = "时长", width = 15)
    @ApiModelProperty(value = "时长")
    private String len;
	/**宽*/
	@Excel(name = "宽", width = 15)
    @ApiModelProperty(value = "宽")
    private Integer width;
	/**高*/
	@Excel(name = "高", width = 15)
    @ApiModelProperty(value = "高")
    private Integer height;
	/**帧率*/
	@Excel(name = "帧率", width = 15)
    @ApiModelProperty(value = "帧率")
    private Double frame;
	/**视频流信息*/
	@Excel(name = "视频流信息", width = 15)
    @ApiModelProperty(value = "视频流信息")
    private String videoStream;
	/**流数量*/
	@Excel(name = "流数量", width = 15)
    @ApiModelProperty(value = "流数量")
    private Integer streamCnt;
	/**全路径*/
	@Excel(name = "全路径", width = 15)
    @ApiModelProperty(value = "全路径")
    private String path;
	/**文件名称*/
	@Excel(name = "文件名称", width = 15)
    @ApiModelProperty(value = "文件名称")
    private String fileName;
	/**格式*/
	@Excel(name = "格式", width = 15)
    @ApiModelProperty(value = "格式")
    private String ext;
	/**别名*/
	@Excel(name = "别名", width = 15)
    @ApiModelProperty(value = "别名")
    private String nameAlise;
	/**别名2*/
	@Excel(name = "别名2", width = 15)
    @ApiModelProperty(value = "别名2")
    private String nameAlise2;
	/**父级目录*/
	@Excel(name = "父级目录", width = 15)
    @ApiModelProperty(value = "父级目录")
    private String nameParent;
	/**CRC*/
	@Excel(name = "CRC", width = 15)
    @ApiModelProperty(value = "CRC")
    private String crc32;
}
