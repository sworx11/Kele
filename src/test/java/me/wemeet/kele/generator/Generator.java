package me.wemeet.kele.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Generator {
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.1.4:3306/kele","root","1172477133wQ?")
                // 全局配置
                .globalConfig((builder) -> builder.author("Quino Wu").outputDir("D://MP"))
                // 包配置
                .packageConfig((builder) -> builder.parent("me.wemeet.kele"))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                        .controllerBuilder().enableRestStyle().enableHyphenStyle()
                        .entityBuilder().enableLombok().addTableFills(
                                new Column("active", FieldFill.INSERT),
                                new Column("create_at", FieldFill.INSERT),
                                new Column("update_at", FieldFill.INSERT_UPDATE)
                        ).build())
                   .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
