
package com.bootx.miniprogram.entity;

import com.bootx.common.BigDecimalNumericFieldBridge;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Entity - 商品
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Indexed
@Entity
public class Product extends BaseEntity<Long> {

	private static final long serialVersionUID = -6977025562650112419L;

	/**
	 * 名称
	 */
	@JsonView(BaseView.class)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Boost(1.5F)
	@NotEmpty
	@Length(max = 200)
	@Column(nullable = false)
	private String name;

	/**
	 * 销售价
	 */
	@JsonView(BaseView.class)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@SortableField
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal price;

	/**
	 * 市场价
	 */
	@JsonView(BaseView.class)
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@NumericField
	@FieldBridge(impl = BigDecimalNumericFieldBridge.class)
	@Column(nullable = false, precision = 21, scale = 6)
	private BigDecimal marketPrice;


	/**
	 * 销量
	 */
	@JsonView(BaseEntity.BaseView.class)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@SortableField
	@Column(nullable = false)
	private Long sales;

	@JsonView(BaseEntity.BaseView.class)
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@SortableField
	@Column(nullable = false)
	private Long stock;

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 *            名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取销售价
	 * 
	 * @return 销售价
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 设置销售价
	 * 
	 * @param price
	 *            销售价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 获取市场价
	 * 
	 * @return 市场价
	 */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	/**
	 * 设置市场价
	 * 
	 * @param marketPrice
	 *            市场价
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Long getSales() {
		return sales;
	}

	public void setSales(Long sales) {
		this.sales = sales;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}
}