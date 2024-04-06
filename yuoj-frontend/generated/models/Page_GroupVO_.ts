/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { GroupVO } from './GroupVO';
import type { OrderItem } from './OrderItem';

export type Page_GroupVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: Array<OrderItem>;
    pages?: number;
    records?: Array<GroupVO>;
    searchCount?: boolean;
    size?: number;
    total?: number;
};
