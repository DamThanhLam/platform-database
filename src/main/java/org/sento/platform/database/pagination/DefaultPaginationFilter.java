package org.sento.platform.database.pagination;

import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.List;

public interface DefaultPaginationFilter {
    List<CriteriaDefinition> queryOperator();
}
