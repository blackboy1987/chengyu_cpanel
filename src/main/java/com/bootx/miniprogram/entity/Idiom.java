package com.bootx.miniprogram.entity;

import com.bootx.common.BaseAttributeConverter;
import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Idiom extends BaseEntity<Long> {

    @NotNull
    @Column(nullable = false)
    @JsonView({BaseEntity.ViewView.class})
    private Integer level;

    @NotEmpty
    @Column(nullable = false, length = 4000)
    @Convert(converter = IdiomsConverter.class)
    @JsonView({BaseEntity.ViewView.class})
    private List<String> idioms = new ArrayList<>();

    @NotEmpty
    @Column(nullable = false, length = 4000)
    @Convert(converter = AnswerConverter.class)
    @JsonView({BaseEntity.ViewView.class})
    private List<String> answer = new ArrayList<>();

    @NotEmpty
    @Column(nullable = false, length = 8000)
    @Convert(converter = GameBoxConverter.class)
    @JsonView({BaseEntity.ViewView.class})
    private List<List<GameBox>> gameBoxes = new ArrayList<>();

    @Column(nullable = false, length = 8000)
    @Convert(converter = GameBox1Converter.class)
    private List<GameBox> gameBoxes1 = new ArrayList<>();


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<String> getIdioms() {
        return idioms;
    }

    public void setIdiom(List<String> idios) {
        this.idioms = idioms;
    }

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    public void setIdioms(List<String> idioms) {
        this.idioms = idioms;
    }

    public List<List<GameBox>> getGameBoxes() {
        return gameBoxes;
    }

    public void setGameBoxes(List<List<GameBox>> gameBoxes) {
        this.gameBoxes = gameBoxes;
    }

    public List<GameBox> getGameBoxes1() {
        return gameBoxes1;
    }

    public void setGameBoxes1(List<GameBox> gameBoxes1) {
        this.gameBoxes1 = gameBoxes1;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class GameBox implements Serializable{

        @JsonView({BaseEntity.ViewView.class})
        private Boolean canSelect;

        @JsonView({BaseEntity.ViewView.class})
        private Boolean show;

        @JsonView({BaseEntity.ViewView.class})
        private String text;

        @JsonView({BaseEntity.ViewView.class})
        private String ans;

        public Boolean getCanSelect() {
            return canSelect;
        }

        public void setCanSelect(Boolean canSelect) {
            this.canSelect = canSelect;
        }

        public Boolean getShow() {
            return show;
        }

        public void setShow(Boolean show) {
            this.show = show;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAns() {
            return ans;
        }

        public void setAns(String ans) {
            this.ans = ans;
        }
    }


    /**
     * 类型转换 - 可选项
     *
     * @author 好源++ Team
     * @version 6.1
     */
    @Converter
    public static class IdiomsConverter extends BaseAttributeConverter<List<String>> {
    }

    /**
     * 类型转换 - 可选项
     *
     * @author 好源++ Team
     * @version 6.1
     */
    @Converter
    public static class AnswerConverter extends BaseAttributeConverter<List<String>> {
    }

    /**
     * 类型转换 - 可选项
     *
     * @author 好源++ Team
     * @version 6.1
     */
    @Converter
    public static class GameBoxConverter extends BaseAttributeConverter<List<List<GameBox>>> {
    }

    /**
     * 类型转换 - 可选项
     *
     * @author 好源++ Team
     * @version 6.1
     */
    @Converter
    public static class GameBox1Converter extends BaseAttributeConverter<List<GameBox>> {
    }
}
