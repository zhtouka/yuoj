/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { OrderItem } from './OrderItem';
import type { UserFriendVO } from './UserFriendVO';

export type Page_UserFriendVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: Array<OrderItem>;
    pages?: number;
    records?: Array<UserFriendVO>;
    searchCount?: boolean;
    size?: number;
    total?: number;
};
