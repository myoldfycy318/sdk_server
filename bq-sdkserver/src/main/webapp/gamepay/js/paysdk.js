webpackJsonp([1, 6], [function (e, t, n) {
    (function (e) {
        "use strict";
        n(2), n(32);
        var t = n(9), r = n(34), i = n(35), o = n(36), a = n(37), s = n(38), l = n(39), u = n(40), c = n(41), d = n(10),
            p = n(42), f = n(30), h = n(43);
        e(function () {
            var n = {
                state: {
                    user: {name: ""},
                    buyerId: "",
                    zoneId: "",
                    price: 0,
                    pay: "qbao",
                    payDesc: "钱宝支付",
                    appCode: "",
                    channelCode: "CHA000001",
                    chargePointName: "充值",
                    payOrigin: "pc",
                    areaId: 0,
                    service: "create_direct_pay_by_user",
                    orderNo: "",
                    transDate: "",
                    payOption: {}
                },
                config: {gameName: "", rate: 0, unit: "", maxCheckTime: 60, checkTime: 0, checkInterval: 1e4},
                els: {
                    otherPriceInput: ".price-other-value",
                    pricesInput: ".price-btn > a",
                    gameSelectButton: ".game-btn > a",
                    areaSelectButton: ".area-btn > a",
                    payInput: ".pay-btn > a",
                    useBqInput: '#pay-qbao-form input[name="useBqFlag"]',
                    useBqTip: "#pay-qbao-form .bq-panel .tip",
                    payPwdTip: "#pay-qbao-form .pay-pwd .tip",
                    gameListPanel: "#game-list-panel",
                    areaListPanel: "#area-list-panel",
                    gamesBtnDropdown: "#games-btn-dropdown",
                    areasBtnDropdown: "#areas-btn-dropdown"
                },
                common: t,
                checkPayedHandle: {},
                init: function () {
                    this.getPayOption(), this.getQueryParams(), this.bindGetPayInfoEvent(), this.bindDropdownEvent(), this.bindSelectPayEvent(), this.renderUserInfo()
                },
                getQueryParams: function () {
                    this.state.user.name = this.common.getQueryString("username"), this.state.buyerId = this.common.getQueryString("buyerId"), this.state.zoneId = this.common.getQueryString("zoneid"), this.state.appCode = this.common.getQueryString("appcode")
                },
                getPayOption: function () {
                    var e = this, t = e.common.config.domain.api + "/bqpay/queryPayOption", n = {}, r = function (t) {
                        e.state.payOption = t.data, setTimeout(function () {
                            e.renderGameList(), e.renderAmountList()
                        }, 200)
                    };
                    e.common.ajaxHelp(t, "GET", n, r)
                },
                getPayInfoAlipay: function (t) {
                    var n = t.common.config.domain.api + "/bqpay/createPcOrder", r = {}, o = function (n) {
                        var r = "https://mapi.alipay.com/gateway.do?" + n.data.payInfo.map(function (e) {
                                return e.name + "=" + encodeURIComponent(e.value)
                            }).join("&");
                        t.state.orderNo = n.data.orderNo;
                        var o = u({kvs: n.data.payInfo});
                        e("#form-panel").html("").html(o), setTimeout(function () {
                            e("#pay-submit").submit();
                            var n = i({href: r});
                            t.common.tipsAlert(null, "订单号：" + t.state.orderNo, "tips-panel", "info", n, null, f), setTimeout(function () {
                                t.bindPayInfoEvent(), t.bindCloseTipBoxEvent()
                            }, 200)
                        }, 500)
                    };
                    r = {
                        appCode: t.state.appCode,
                        chargePointAmount: t.state.price,
                        payType: 1,
                        channelCode: t.state.channelCode,
                        chargePointName: t.state.chargePointName,
                        zoneId: t.state.zoneId,
                        payOrigin: t.state.payOrigin,
                        buyerId: t.state.buyerId
                    }, t.common.ajaxHelp(n, "POST", r, o)
                },
                getPayInfoQBao: function (t) {
                    var n = t.common.config.domain.api + "/sdkpay/v10/createWebGameOrder", r = {}, i = function (n) {
                        t.state.orderNo = n.data.sdkflowId, t.state.transDate = n.data.transDate;
                        var r, i = n.data, o = {}, a = i.currency.scale || 1;
                        o = {
                            pay: t.state.payDesc,
                            qbAmountDesc: i.qbAmount / a,
                            isBqFlag: 1 === i.useBqFlag && i.bqAmount > 0,
                            bqAmountDesc: i.bqAmount,
                            propPrice: t.state.price.toFixed(2)
                        }, r = c(e.extend(n.data, o)), setTimeout(function () {
                            t.common.tipsAlert("支付信息", null, "pop-panel", null, r, null, d), setTimeout(function () {
                                t.bindCommitQbao()
                            }, 200)
                        }, 500)
                    };
                    r = {
                        appCode: t.state.appCode,
                        chargePointAmount: t.state.price,
                        channelCode: t.state.channelCode,
                        chargePointName: t.state.chargePointName,
                        zoneId: t.state.zoneId,
                        payOrigin: t.state.payOrigin,
                        buyerId: t.state.buyerId
                    }, t.common.ajaxHelp(n, "POST", r, i)
                },
                bindGetPayInfoEvent: function () {
                    var t = this;
                    e("#pay-btn").on("click", function () {
                        if (t.state.price <= 0)return void t.common.tipsAlert(null, "充值金额不能为零", "tips-panel", "info", null, null, f);
                        switch (t.state.pay) {
                            case"alipay":
                                t.state.payDesc = "", t.getPayInfoAlipay(t);
                                break;
                            case"qbao":
                                t.state.payDesc = "钱宝支付", t.getPayInfoQBao(t)
                        }
                    })
                },
                bindSelectPriceEvent: function () {
                    var t = this;
                    e(t.els.pricesInput).on("click", function () {
                        var n = parseInt(e(this).attr("data-value"));
                        e(t.els.otherPriceInput).removeClass("selected").val(""), e(".prices > ul > li > label > .cur").removeClass("cur"), e(this).addClass("cur"), t.state.price = n, t.renderPriceTip()
                    })
                },
                bindSelectGameEvent: function () {
                    var t = this;
                    e(t.els.gameSelectButton).on("click", function () {
                        var n = e(this).attr("data-app-code");
                        e(t.els.gameListPanel + " > .panel > ul > li > label > .cur").removeClass("cur"), e(this).addClass("cur"), t.afterSelectedGame(this), t.state.appCode = n, t.renderZoneList(), e(t.els.gameListPanel).hide()
                    })
                },
                bindSelectAreaEvent: function () {
                    var t = this;
                    e(t.els.areaSelectButton).on("click", function () {
                        e(t.els.areaListPanel + " > .panel > ul > li > label > .cur").removeClass("cur"), e(this).addClass("cur"), t.afterSelectedZone(this), e(t.els.areaListPanel).hide()
                    })
                },
                bindSelectPayEvent: function () {
                    var t = this;
                    e(t.els.payInput).on("click", function () {
                        var n = e(this).attr("data-value");
                        e(".pays > ul > li > label > .cur").removeClass("cur"), e(this).addClass("cur"), t.state.pay = n
                    })
                },
                bindOtherPriceEntryEvent: function () {
                    var t = this;
                    e(this.els.otherPriceInput).on("focus", function () {
                        e(".prices > ul > li > label > .cur").removeClass("cur"), e(this).addClass("selected")
                    }), e(this.els.otherPriceInput).on("blur", function () {
                        var n = e(this).val();
                        (n < 10 || n > 1e5) && (e(this).val(""), t.state.price = 0, t.renderPriceTip(), t.common.tipsAlert(null, "请输入大于 10，小于 100000 的整数", "tips-panel", "info", null, null, f))
                    })
                },
                bindOtherPriceEvent: function () {
                    var t = this;
                    e(this.els.otherPriceInput).on("change", function () {
                        var n = parseInt(e(this).val());
                        t.state.price = n, t.renderPriceTip()
                    })
                },
                bindPayInfoEvent: function () {
                    var t = this;
                    e("#already-pay-btn").on("click", t.checkPayedCb.bind(t, "check"))
                },
                bindCloseTipBoxEvent: function () {
                    e("#pay-close-btn").on("click", function () {
                        e(".util-tips-box").remove()
                    })
                },
                bindCommitQbao: function () {
                    var t = this;
                    e("#pay-qbao-form").on("submit", function (n) {
                        n.preventDefault();
                        var r = t.common.config.domain.api + "/sdkpay/v10/dopay", i = {}, o = function (e) {
                            var n = parseInt(e.responseCode);
                            1e3 === n ? t.common.tipsAlert(null, "支付成功", "tips-panel", "info", null, null, f) : 1005 === n && t.showTip(t.els.payPwdTip, e.errorMsg)
                        };
                        return i = {
                            appCode: t.state.appCode,
                            sdkflowId: t.state.orderNo,
                            transDate: t.state.transDate,
                            payOrigin: t.state.payOrigin,
                            useBqFlag: 1 === e(this).find('input[name="useBqFlag"]:checked').length ? 1 : 0,
                            transPassWord: e(this).find('input[name="transPassWord"]').val()
                        }, i.transPassWord ? void t.common.ajaxHelp(r, "POST", i, o, !0) : void t.showTip(t.els.payPwdTip, "请输入交易密码")
                    })
                },
                bindDropdownEvent: function () {
                    var t = this;
                    e(t.els.gamesBtnDropdown).on("click", function () {
                        t.dropdownShow(this, t.els.gameListPanel)
                    }), e(t.els.areasBtnDropdown).on("click", function () {
                        t.dropdownShow(this, t.els.areaListPanel)
                    })
                },
                showTip: function (t, n) {
                    e(t).html(n).fadeIn(100).addClass("activity"), setTimeout(function () {
                        e(t).html("").hide().removeClass("activity")
                    }, 2500)
                },
                checkPayed: function () {
                    var e = this;
                    e.checkPayedHandle = setInterval(e.checkPayedCb.bind(e), e.config.checkInterval)
                },
                checkPayedCb: function (e) {
                    var t = this, n = t.common.config.domain.api + "/bqpay/isOrderPay", r = {orderNo: t.state.orderNo},
                        s = function (n) {
                            var r = "";
                            if ("true" === n.data.isPay) clearInterval(t.checkPayedHandle), r = a({}), t.common.tipsAlert(null, "支付成功", "tips-panel", "info", null, null, f), setTimeout(function () {
                                t.bindCloseTipBoxEvent()
                            }, 200); else if ("check" === e) {
                                var s = i({});
                                t.common.tipsAlert(null, "订单号：" + t.state.orderNo + "，尚未支付成功，请稍后再试", "tips-panel", "info", s, null, f), setTimeout(function () {
                                    t.bindPayInfoEvent(), t.bindCloseTipBoxEvent()
                                }, 200)
                            } else t.config.checkTime >= t.config.maxCheckTime && (clearInterval(t.checkPayedHandle), r = o({}), t.common.tipsAlert(null, "支付失败", "tips-panel", "fail", r, null, f), setTimeout(function () {
                                t.bindCloseTipBoxEvent()
                            }, 200)), t.config.checkTime++
                        };
                    t.common.ajaxHelp(n, "GET", r, s)
                },
                dropdownOutsideClick: function (t, n) {
                    var r = e(n.target);
                    "focusin" == n.type || r.closest(".dropdown").length || this.dropdownHide(t)
                },
                dropdownShow: function (t, n) {
                    var r = e(t).offset();
                    e(n).css("left", r.left).css("top", r.top + 55).show(), this._outsideClickProxy = e.proxy(function (e, t) {
                        this.dropdownOutsideClick(e, t)
                    }, this, n), e(document).on("mousedown.dropdown", this._outsideClickProxy).on("touchend.dropdown", this._outsideClickProxy).on("click.dropdown", "[data-toggle=dropdown]", this._outsideClickProxy).on("focusin.dropdown", this._outsideClickProxy)
                },
                dropdownHide: function (t) {
                    e(t).hide(), e(document).off(".dropdown")
                },
                renderUserInfo: function () {
                    if (this.state.user.name) {
                        var t = h({username: this.state.user.name});
                        e(".user-info").html("").html(t)
                    }
                },
                renderPriceTip: function () {
                    var t, n = "", r = this.state.price;
                    t = {
                        rmb: r,
                        points: r * this.config.rate,
                        unit: this.config.unit,
                        gameName: this.config.gameName
                    }, n = p(t), e(".prices > .tip").html("").html(n)
                },
                renderAmountList: function () {
                    var t, n = "", r = [];
                    r = this.state.payOption.amountList, n = s({amountList: r}), e("#amount-list").html("").html(n), t = e(this.els.pricesInput).first(), e(t).addClass("cur"), this.state.price = parseFloat(e(t).attr("data-value")), this.renderPriceTip(), this.bindSelectPriceEvent(), this.bindOtherPriceEntryEvent(), this.bindOtherPriceEvent()
                },
                renderGameList: function () {
                    var t = {length: 0}, n = "";
                    n = r({gameList: this.state.payOption.gameList}), e(this.els.gameListPanel + " .panel").html("").html(n), this.state.appCode && (t = e(this.els.gameListPanel + ' a[data-app-code="' + this.state.appCode + '"]')), t = t.length > 0 ? t : e(this.els.gameSelectButton).first(), e(t).addClass("cur"), this.afterSelectedGame(t), this.renderZoneList(), this.bindSelectGameEvent()
                },
                renderZoneList: function () {
                    var t, n, r = this, i = "", o = {length: 0};
                    n = "area-list-tpl", r.state.appCode && (t = r.state.payOption.gameList.filter(function (e) {
                        return e.appCode === r.state.appCode
                    }), i = l({areaList: t[0].zones}), e(this.els.areaListPanel + " .panel").html("").html(i), this.state.zoneId && (o = e(this.els.areaListPanel + ' a[data-zone-id="' + this.state.zoneId + '"]')), o = o.length > 0 ? o : e(this.els.areaSelectButton).first(), e(o).addClass("cur"), r.afterSelectedZone(o), r.bindSelectAreaEvent())
                },
                afterSelectedGame: function (t) {
                    e(this.els.gamesBtnDropdown + " span").html("").html(e(t).html()), this.state.appCode = e(t).attr("data-app-code"), this.config.gameName = '"' + e(t).attr("data-app-name") + '"', this.config.rate = e(t).attr("data-charge-point"), this.config.unit = e(t).attr("data-unit"), this.renderPriceTip()
                },
                afterSelectedZone: function (t) {
                    e(this.els.areasBtnDropdown + " span").html("").html(e(t).html()), this.state.zoneId = e(t).attr("data-zone-id")
                }
            };
            n.init()
        })
    }).call(t, n(1))
}, function (e, t, n) {
    var r, i;
    /*!
     * jQuery JavaScript Library v1.12.4
     * http://jquery.com/
     *
     * Includes Sizzle.js
     * http://sizzlejs.com/
     *
     * Copyright jQuery Foundation and other contributors
     * Released under the MIT license
     * http://jquery.org/license
     *
     * Date: 2016-05-20T17:17Z
     */
    !function (t, n) {
        "object" == typeof e && "object" == typeof e.exports ? e.exports = t.document ? n(t, !0) : function (e) {
            if (!e.document)throw new Error("jQuery requires a window with a document");
            return n(e)
        } : n(t)
    }("undefined" != typeof window ? window : this, function (n, o) {
        function a(e) {
            var t = !!e && "length" in e && e.length, n = ge.type(e);
            return "function" !== n && !ge.isWindow(e) && ("array" === n || 0 === t || "number" == typeof t && t > 0 && t - 1 in e)
        }

        function s(e, t, n) {
            if (ge.isFunction(t))return ge.grep(e, function (e, r) {
                return !!t.call(e, r, e) !== n
            });
            if (t.nodeType)return ge.grep(e, function (e) {
                return e === t !== n
            });
            if ("string" == typeof t) {
                if (Se.test(t))return ge.filter(t, e, n);
                t = ge.filter(t, e)
            }
            return ge.grep(e, function (e) {
                return ge.inArray(e, t) > -1 !== n
            })
        }

        function l(e, t) {
            do e = e[t]; while (e && 1 !== e.nodeType);
            return e
        }

        function u(e) {
            var t = {};
            return ge.each(e.match(_e) || [], function (e, n) {
                t[n] = !0
            }), t
        }

        function c() {
            se.addEventListener ? (se.removeEventListener("DOMContentLoaded", d), n.removeEventListener("load", d)) : (se.detachEvent("onreadystatechange", d), n.detachEvent("onload", d))
        }

        function d() {
            (se.addEventListener || "load" === n.event.type || "complete" === se.readyState) && (c(), ge.ready())
        }

        function p(e, t, n) {
            if (void 0 === n && 1 === e.nodeType) {
                var r = "data-" + t.replace(Ie, "-$1").toLowerCase();
                if (n = e.getAttribute(r), "string" == typeof n) {
                    try {
                        n = "true" === n || "false" !== n && ("null" === n ? null : +n + "" === n ? +n : Oe.test(n) ? ge.parseJSON(n) : n)
                    } catch (e) {
                    }
                    ge.data(e, t, n)
                } else n = void 0
            }
            return n
        }

        function f(e) {
            var t;
            for (t in e)if (("data" !== t || !ge.isEmptyObject(e[t])) && "toJSON" !== t)return !1;
            return !0
        }

        function h(e, t, n, r) {
            if (Me(e)) {
                var i, o, a = ge.expando, s = e.nodeType, l = s ? ge.cache : e, u = s ? e[a] : e[a] && a;
                if (u && l[u] && (r || l[u].data) || void 0 !== n || "string" != typeof t)return u || (u = s ? e[a] = ae.pop() || ge.guid++ : a), l[u] || (l[u] = s ? {} : {toJSON: ge.noop}), "object" != typeof t && "function" != typeof t || (r ? l[u] = ge.extend(l[u], t) : l[u].data = ge.extend(l[u].data, t)), o = l[u], r || (o.data || (o.data = {}), o = o.data), void 0 !== n && (o[ge.camelCase(t)] = n), "string" == typeof t ? (i = o[t], null == i && (i = o[ge.camelCase(t)])) : i = o, i
            }
        }

        function m(e, t, n) {
            if (Me(e)) {
                var r, i, o = e.nodeType, a = o ? ge.cache : e, s = o ? e[ge.expando] : ge.expando;
                if (a[s]) {
                    if (t && (r = n ? a[s] : a[s].data)) {
                        ge.isArray(t) ? t = t.concat(ge.map(t, ge.camelCase)) : t in r ? t = [t] : (t = ge.camelCase(t), t = t in r ? [t] : t.split(" ")), i = t.length;
                        for (; i--;)delete r[t[i]];
                        if (n ? !f(r) : !ge.isEmptyObject(r))return
                    }
                    (n || (delete a[s].data, f(a[s]))) && (o ? ge.cleanData([e], !0) : me.deleteExpando || a != a.window ? delete a[s] : a[s] = void 0)
                }
            }
        }

        function v(e, t, n, r) {
            var i, o = 1, a = 20, s = r ? function () {
                    return r.cur()
                } : function () {
                    return ge.css(e, t, "")
                }, l = s(), u = n && n[3] || (ge.cssNumber[t] ? "" : "px"),
                c = (ge.cssNumber[t] || "px" !== u && +l) && Be.exec(ge.css(e, t));
            if (c && c[3] !== u) {
                u = u || c[3], n = n || [], c = +l || 1;
                do o = o || ".5", c /= o, ge.style(e, t, c + u); while (o !== (o = s() / l) && 1 !== o && --a)
            }
            return n && (c = +c || +l || 0, i = n[1] ? c + (n[1] + 1) * n[2] : +n[2], r && (r.unit = u, r.start = c, r.end = i)), i
        }

        function g(e) {
            var t = Ue.split("|"), n = e.createDocumentFragment();
            if (n.createElement)for (; t.length;)n.createElement(t.pop());
            return n
        }

        function y(e, t) {
            var n, r, i = 0,
                o = "undefined" != typeof e.getElementsByTagName ? e.getElementsByTagName(t || "*") : "undefined" != typeof e.querySelectorAll ? e.querySelectorAll(t || "*") : void 0;
            if (!o)for (o = [], n = e.childNodes || e; null != (r = n[i]); i++)!t || ge.nodeName(r, t) ? o.push(r) : ge.merge(o, y(r, t));
            return void 0 === t || t && ge.nodeName(e, t) ? ge.merge([e], o) : o
        }

        function b(e, t) {
            for (var n, r = 0; null != (n = e[r]); r++)ge._data(n, "globalEval", !t || ge._data(t[r], "globalEval"))
        }

        function x(e) {
            $e.test(e.type) && (e.defaultChecked = e.checked)
        }

        function w(e, t, n, r, i) {
            for (var o, a, s, l, u, c, d, p = e.length, f = g(t), h = [], m = 0; m < p; m++)if (a = e[m], a || 0 === a)if ("object" === ge.type(a)) ge.merge(h, a.nodeType ? [a] : a); else if (Je.test(a)) {
                for (l = l || f.appendChild(t.createElement("div")), u = (Xe.exec(a) || ["", ""])[1].toLowerCase(), d = Qe[u] || Qe._default, l.innerHTML = d[1] + ge.htmlPrefilter(a) + d[2], o = d[0]; o--;)l = l.lastChild;
                if (!me.leadingWhitespace && Ge.test(a) && h.push(t.createTextNode(Ge.exec(a)[0])), !me.tbody)for (a = "table" !== u || Ye.test(a) ? "<table>" !== d[1] || Ye.test(a) ? 0 : l : l.firstChild, o = a && a.childNodes.length; o--;)ge.nodeName(c = a.childNodes[o], "tbody") && !c.childNodes.length && a.removeChild(c);
                for (ge.merge(h, l.childNodes), l.textContent = ""; l.firstChild;)l.removeChild(l.firstChild);
                l = f.lastChild
            } else h.push(t.createTextNode(a));
            for (l && f.removeChild(l), me.appendChecked || ge.grep(y(h, "input"), x), m = 0; a = h[m++];)if (r && ge.inArray(a, r) > -1) i && i.push(a); else if (s = ge.contains(a.ownerDocument, a), l = y(f.appendChild(a), "script"), s && b(l), n)for (o = 0; a = l[o++];)Ve.test(a.type || "") && n.push(a);
            return l = null, f
        }

        function C() {
            return !0
        }

        function T() {
            return !1
        }

        function k() {
            try {
                return se.activeElement
            } catch (e) {
            }
        }

        function E(e, t, n, r, i, o) {
            var a, s;
            if ("object" == typeof t) {
                "string" != typeof n && (r = r || n, n = void 0);
                for (s in t)E(e, s, n, r, t[s], o);
                return e
            }
            if (null == r && null == i ? (i = n, r = n = void 0) : null == i && ("string" == typeof n ? (i = r, r = void 0) : (i = r, r = n, n = void 0)), i === !1) i = T; else if (!i)return e;
            return 1 === o && (a = i, i = function (e) {
                return ge().off(e), a.apply(this, arguments)
            }, i.guid = a.guid || (a.guid = ge.guid++)), e.each(function () {
                ge.event.add(this, t, i, r, n)
            })
        }

        function N(e, t) {
            return ge.nodeName(e, "table") && ge.nodeName(11 !== t.nodeType ? t : t.firstChild, "tr") ? e.getElementsByTagName("tbody")[0] || e.appendChild(e.ownerDocument.createElement("tbody")) : e
        }

        function S(e) {
            return e.type = (null !== ge.find.attr(e, "type")) + "/" + e.type, e
        }

        function A(e) {
            var t = lt.exec(e.type);
            return t ? e.type = t[1] : e.removeAttribute("type"), e
        }

        function P(e, t) {
            if (1 === t.nodeType && ge.hasData(e)) {
                var n, r, i, o = ge._data(e), a = ge._data(t, o), s = o.events;
                if (s) {
                    delete a.handle, a.events = {};
                    for (n in s)for (r = 0, i = s[n].length; r < i; r++)ge.event.add(t, n, s[n][r])
                }
                a.data && (a.data = ge.extend({}, a.data))
            }
        }

        function D(e, t) {
            var n, r, i;
            if (1 === t.nodeType) {
                if (n = t.nodeName.toLowerCase(), !me.noCloneEvent && t[ge.expando]) {
                    i = ge._data(t);
                    for (r in i.events)ge.removeEvent(t, r, i.handle);
                    t.removeAttribute(ge.expando)
                }
                "script" === n && t.text !== e.text ? (S(t).text = e.text, A(t)) : "object" === n ? (t.parentNode && (t.outerHTML = e.outerHTML), me.html5Clone && e.innerHTML && !ge.trim(t.innerHTML) && (t.innerHTML = e.innerHTML)) : "input" === n && $e.test(e.type) ? (t.defaultChecked = t.checked = e.checked, t.value !== e.value && (t.value = e.value)) : "option" === n ? t.defaultSelected = t.selected = e.defaultSelected : "input" !== n && "textarea" !== n || (t.defaultValue = e.defaultValue)
            }
        }

        function L(e, t, n, r) {
            t = ue.apply([], t);
            var i, o, a, s, l, u, c = 0, d = e.length, p = d - 1, f = t[0], h = ge.isFunction(f);
            if (h || d > 1 && "string" == typeof f && !me.checkClone && st.test(f))return e.each(function (i) {
                var o = e.eq(i);
                h && (t[0] = f.call(this, i, o.html())), L(o, t, n, r)
            });
            if (d && (u = w(t, e[0].ownerDocument, !1, e, r), i = u.firstChild, 1 === u.childNodes.length && (u = i), i || r)) {
                for (s = ge.map(y(u, "script"), S), a = s.length; c < d; c++)o = u, c !== p && (o = ge.clone(o, !0, !0), a && ge.merge(s, y(o, "script"))), n.call(e[c], o, c);
                if (a)for (l = s[s.length - 1].ownerDocument, ge.map(s, A), c = 0; c < a; c++)o = s[c], Ve.test(o.type || "") && !ge._data(o, "globalEval") && ge.contains(l, o) && (o.src ? ge._evalUrl && ge._evalUrl(o.src) : ge.globalEval((o.text || o.textContent || o.innerHTML || "").replace(ut, "")));
                u = i = null
            }
            return e
        }

        function j(e, t, n) {
            for (var r, i = t ? ge.filter(t, e) : e, o = 0; null != (r = i[o]); o++)n || 1 !== r.nodeType || ge.cleanData(y(r)), r.parentNode && (n && ge.contains(r.ownerDocument, r) && b(y(r, "script")), r.parentNode.removeChild(r));
            return e
        }

        function _(e, t) {
            var n = ge(t.createElement(e)).appendTo(t.body), r = ge.css(n[0], "display");
            return n.detach(), r
        }

        function H(e) {
            var t = se, n = ft[e];
            return n || (n = _(e, t), "none" !== n && n || (pt = (pt || ge("<iframe frameborder='0' width='0' height='0'/>")).appendTo(t.documentElement), t = (pt[0].contentWindow || pt[0].contentDocument).document, t.write(), t.close(), n = _(e, t), pt.detach()), ft[e] = n), n
        }

        function q(e, t) {
            return {
                get: function () {
                    return e() ? void delete this.get : (this.get = t).apply(this, arguments)
                }
            }
        }

        function M(e) {
            if (e in At)return e;
            for (var t = e.charAt(0).toUpperCase() + e.slice(1), n = St.length; n--;)if (e = St[n] + t, e in At)return e
        }

        function O(e, t) {
            for (var n, r, i, o = [], a = 0, s = e.length; a < s; a++)r = e[a], r.style && (o[a] = ge._data(r, "olddisplay"), n = r.style.display, t ? (o[a] || "none" !== n || (r.style.display = ""), "" === r.style.display && We(r) && (o[a] = ge._data(r, "olddisplay", H(r.nodeName)))) : (i = We(r), (n && "none" !== n || !i) && ge._data(r, "olddisplay", i ? n : ge.css(r, "display"))));
            for (a = 0; a < s; a++)r = e[a], r.style && (t && "none" !== r.style.display && "" !== r.style.display || (r.style.display = t ? o[a] || "" : "none"));
            return e
        }

        function I(e, t, n) {
            var r = kt.exec(t);
            return r ? Math.max(0, r[1] - (n || 0)) + (r[2] || "px") : t
        }

        function F(e, t, n, r, i) {
            for (var o = n === (r ? "border" : "content") ? 4 : "width" === t ? 1 : 0, a = 0; o < 4; o += 2)"margin" === n && (a += ge.css(e, n + Re[o], !0, i)), r ? ("content" === n && (a -= ge.css(e, "padding" + Re[o], !0, i)), "margin" !== n && (a -= ge.css(e, "border" + Re[o] + "Width", !0, i))) : (a += ge.css(e, "padding" + Re[o], !0, i), "padding" !== n && (a += ge.css(e, "border" + Re[o] + "Width", !0, i)));
            return a
        }

        function B(e, t, n) {
            var r = !0, i = "width" === t ? e.offsetWidth : e.offsetHeight, o = yt(e),
                a = me.boxSizing && "border-box" === ge.css(e, "boxSizing", !1, o);
            if (i <= 0 || null == i) {
                if (i = bt(e, t, o), (i < 0 || null == i) && (i = e.style[t]), mt.test(i))return i;
                r = a && (me.boxSizingReliable() || i === e.style[t]), i = parseFloat(i) || 0
            }
            return i + F(e, t, n || (a ? "border" : "content"), r, o) + "px"
        }

        function R(e, t, n, r, i) {
            return new R.prototype.init(e, t, n, r, i)
        }

        function W() {
            return n.setTimeout(function () {
                Pt = void 0
            }), Pt = ge.now()
        }

        function z(e, t) {
            var n, r = {height: e}, i = 0;
            for (t = t ? 1 : 0; i < 4; i += 2 - t)n = Re[i], r["margin" + n] = r["padding" + n] = e;
            return t && (r.opacity = r.width = e), r
        }

        function $(e, t, n) {
            for (var r, i = (G.tweeners[t] || []).concat(G.tweeners["*"]), o = 0, a = i.length; o < a; o++)if (r = i[o].call(n, t, e))return r
        }

        function X(e, t, n) {
            var r, i, o, a, s, l, u, c, d = this, p = {}, f = e.style, h = e.nodeType && We(e),
                m = ge._data(e, "fxshow");
            n.queue || (s = ge._queueHooks(e, "fx"), null == s.unqueued && (s.unqueued = 0, l = s.empty.fire, s.empty.fire = function () {
                s.unqueued || l()
            }), s.unqueued++, d.always(function () {
                d.always(function () {
                    s.unqueued--, ge.queue(e, "fx").length || s.empty.fire()
                })
            })), 1 === e.nodeType && ("height" in t || "width" in t) && (n.overflow = [f.overflow, f.overflowX, f.overflowY], u = ge.css(e, "display"), c = "none" === u ? ge._data(e, "olddisplay") || H(e.nodeName) : u, "inline" === c && "none" === ge.css(e, "float") && (me.inlineBlockNeedsLayout && "inline" !== H(e.nodeName) ? f.zoom = 1 : f.display = "inline-block")), n.overflow && (f.overflow = "hidden", me.shrinkWrapBlocks() || d.always(function () {
                f.overflow = n.overflow[0], f.overflowX = n.overflow[1], f.overflowY = n.overflow[2]
            }));
            for (r in t)if (i = t[r], Lt.exec(i)) {
                if (delete t[r], o = o || "toggle" === i, i === (h ? "hide" : "show")) {
                    if ("show" !== i || !m || void 0 === m[r])continue;
                    h = !0
                }
                p[r] = m && m[r] || ge.style(e, r)
            } else u = void 0;
            if (ge.isEmptyObject(p)) "inline" === ("none" === u ? H(e.nodeName) : u) && (f.display = u); else {
                m ? "hidden" in m && (h = m.hidden) : m = ge._data(e, "fxshow", {}), o && (m.hidden = !h), h ? ge(e).show() : d.done(function () {
                    ge(e).hide()
                }), d.done(function () {
                    var t;
                    ge._removeData(e, "fxshow");
                    for (t in p)ge.style(e, t, p[t])
                });
                for (r in p)a = $(h ? m[r] : 0, r, d), r in m || (m[r] = a.start, h && (a.end = a.start, a.start = "width" === r || "height" === r ? 1 : 0))
            }
        }

        function V(e, t) {
            var n, r, i, o, a;
            for (n in e)if (r = ge.camelCase(n), i = t[r], o = e[n], ge.isArray(o) && (i = o[1], o = e[n] = o[0]), n !== r && (e[r] = o, delete e[n]), a = ge.cssHooks[r], a && "expand" in a) {
                o = a.expand(o), delete e[r];
                for (n in o)n in e || (e[n] = o[n], t[n] = i)
            } else t[r] = i
        }

        function G(e, t, n) {
            var r, i, o = 0, a = G.prefilters.length, s = ge.Deferred().always(function () {
                delete l.elem
            }), l = function () {
                if (i)return !1;
                for (var t = Pt || W(), n = Math.max(0, u.startTime + u.duration - t), r = n / u.duration || 0, o = 1 - r, a = 0, l = u.tweens.length; a < l; a++)u.tweens[a].run(o);
                return s.notifyWith(e, [u, o, n]), o < 1 && l ? n : (s.resolveWith(e, [u]), !1)
            }, u = s.promise({
                elem: e,
                props: ge.extend({}, t),
                opts: ge.extend(!0, {specialEasing: {}, easing: ge.easing._default}, n),
                originalProperties: t,
                originalOptions: n,
                startTime: Pt || W(),
                duration: n.duration,
                tweens: [],
                createTween: function (t, n) {
                    var r = ge.Tween(e, u.opts, t, n, u.opts.specialEasing[t] || u.opts.easing);
                    return u.tweens.push(r), r
                },
                stop: function (t) {
                    var n = 0, r = t ? u.tweens.length : 0;
                    if (i)return this;
                    for (i = !0; n < r; n++)u.tweens[n].run(1);
                    return t ? (s.notifyWith(e, [u, 1, 0]), s.resolveWith(e, [u, t])) : s.rejectWith(e, [u, t]), this
                }
            }), c = u.props;
            for (V(c, u.opts.specialEasing); o < a; o++)if (r = G.prefilters[o].call(u, e, c, u.opts))return ge.isFunction(r.stop) && (ge._queueHooks(u.elem, u.opts.queue).stop = ge.proxy(r.stop, r)), r;
            return ge.map(c, $, u), ge.isFunction(u.opts.start) && u.opts.start.call(e, u), ge.fx.timer(ge.extend(l, {
                elem: e,
                anim: u,
                queue: u.opts.queue
            })), u.progress(u.opts.progress).done(u.opts.done, u.opts.complete).fail(u.opts.fail).always(u.opts.always)
        }

        function U(e) {
            return ge.attr(e, "class") || ""
        }

        function Q(e) {
            return function (t, n) {
                "string" != typeof t && (n = t, t = "*");
                var r, i = 0, o = t.toLowerCase().match(_e) || [];
                if (ge.isFunction(n))for (; r = o[i++];)"+" === r.charAt(0) ? (r = r.slice(1) || "*", (e[r] = e[r] || []).unshift(n)) : (e[r] = e[r] || []).push(n)
            }
        }

        function J(e, t, n, r) {
            function i(s) {
                var l;
                return o[s] = !0, ge.each(e[s] || [], function (e, s) {
                    var u = s(t, n, r);
                    return "string" != typeof u || a || o[u] ? a ? !(l = u) : void 0 : (t.dataTypes.unshift(u), i(u), !1)
                }), l
            }

            var o = {}, a = e === nn;
            return i(t.dataTypes[0]) || !o["*"] && i("*")
        }

        function Y(e, t) {
            var n, r, i = ge.ajaxSettings.flatOptions || {};
            for (r in t)void 0 !== t[r] && ((i[r] ? e : n || (n = {}))[r] = t[r]);
            return n && ge.extend(!0, e, n), e
        }

        function Z(e, t, n) {
            for (var r, i, o, a, s = e.contents, l = e.dataTypes; "*" === l[0];)l.shift(), void 0 === i && (i = e.mimeType || t.getResponseHeader("Content-Type"));
            if (i)for (a in s)if (s[a] && s[a].test(i)) {
                l.unshift(a);
                break
            }
            if (l[0] in n) o = l[0]; else {
                for (a in n) {
                    if (!l[0] || e.converters[a + " " + l[0]]) {
                        o = a;
                        break
                    }
                    r || (r = a)
                }
                o = o || r
            }
            if (o)return o !== l[0] && l.unshift(o), n[o]
        }

        function K(e, t, n, r) {
            var i, o, a, s, l, u = {}, c = e.dataTypes.slice();
            if (c[1])for (a in e.converters)u[a.toLowerCase()] = e.converters[a];
            for (o = c.shift(); o;)if (e.responseFields[o] && (n[e.responseFields[o]] = t), !l && r && e.dataFilter && (t = e.dataFilter(t, e.dataType)), l = o, o = c.shift())if ("*" === o) o = l; else if ("*" !== l && l !== o) {
                if (a = u[l + " " + o] || u["* " + o], !a)for (i in u)if (s = i.split(" "), s[1] === o && (a = u[l + " " + s[0]] || u["* " + s[0]])) {
                    a === !0 ? a = u[i] : u[i] !== !0 && (o = s[0], c.unshift(s[1]));
                    break
                }
                if (a !== !0)if (a && e.throws) t = a(t); else try {
                    t = a(t)
                } catch (e) {
                    return {state: "parsererror", error: a ? e : "No conversion from " + l + " to " + o}
                }
            }
            return {state: "success", data: t}
        }

        function ee(e) {
            return e.style && e.style.display || ge.css(e, "display")
        }

        function te(e) {
            if (!ge.contains(e.ownerDocument || se, e))return !0;
            for (; e && 1 === e.nodeType;) {
                if ("none" === ee(e) || "hidden" === e.type)return !0;
                e = e.parentNode
            }
            return !1
        }

        function ne(e, t, n, r) {
            var i;
            if (ge.isArray(t)) ge.each(t, function (t, i) {
                n || ln.test(e) ? r(e, i) : ne(e + "[" + ("object" == typeof i && null != i ? t : "") + "]", i, n, r)
            }); else if (n || "object" !== ge.type(t)) r(e, t); else for (i in t)ne(e + "[" + i + "]", t[i], n, r)
        }

        function re() {
            try {
                return new n.XMLHttpRequest
            } catch (e) {
            }
        }

        function ie() {
            try {
                return new n.ActiveXObject("Microsoft.XMLHTTP")
            } catch (e) {
            }
        }

        function oe(e) {
            return ge.isWindow(e) ? e : 9 === e.nodeType && (e.defaultView || e.parentWindow)
        }

        var ae = [], se = n.document, le = ae.slice, ue = ae.concat, ce = ae.push, de = ae.indexOf, pe = {},
            fe = pe.toString, he = pe.hasOwnProperty, me = {}, ve = "1.12.4", ge = function (e, t) {
                return new ge.fn.init(e, t)
            }, ye = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, be = /^-ms-/, xe = /-([\da-z])/gi, we = function (e, t) {
                return t.toUpperCase()
            };
        ge.fn = ge.prototype = {
            jquery: ve, constructor: ge, selector: "", length: 0, toArray: function () {
                return le.call(this)
            }, get: function (e) {
                return null != e ? e < 0 ? this[e + this.length] : this[e] : le.call(this)
            }, pushStack: function (e) {
                var t = ge.merge(this.constructor(), e);
                return t.prevObject = this, t.context = this.context, t
            }, each: function (e) {
                return ge.each(this, e)
            }, map: function (e) {
                return this.pushStack(ge.map(this, function (t, n) {
                    return e.call(t, n, t)
                }))
            }, slice: function () {
                return this.pushStack(le.apply(this, arguments))
            }, first: function () {
                return this.eq(0)
            }, last: function () {
                return this.eq(-1)
            }, eq: function (e) {
                var t = this.length, n = +e + (e < 0 ? t : 0);
                return this.pushStack(n >= 0 && n < t ? [this[n]] : [])
            }, end: function () {
                return this.prevObject || this.constructor()
            }, push: ce, sort: ae.sort, splice: ae.splice
        }, ge.extend = ge.fn.extend = function () {
            var e, t, n, r, i, o, a = arguments[0] || {}, s = 1, l = arguments.length, u = !1;
            for ("boolean" == typeof a && (u = a, a = arguments[s] || {}, s++), "object" == typeof a || ge.isFunction(a) || (a = {}), s === l && (a = this, s--); s < l; s++)if (null != (i = arguments[s]))for (r in i)e = a[r], n = i[r], a !== n && (u && n && (ge.isPlainObject(n) || (t = ge.isArray(n))) ? (t ? (t = !1, o = e && ge.isArray(e) ? e : []) : o = e && ge.isPlainObject(e) ? e : {}, a[r] = ge.extend(u, o, n)) : void 0 !== n && (a[r] = n));
            return a
        }, ge.extend({
            expando: "jQuery" + (ve + Math.random()).replace(/\D/g, ""), isReady: !0, error: function (e) {
                throw new Error(e)
            }, noop: function () {
            }, isFunction: function (e) {
                return "function" === ge.type(e)
            }, isArray: Array.isArray || function (e) {
                return "array" === ge.type(e)
            }, isWindow: function (e) {
                return null != e && e == e.window
            }, isNumeric: function (e) {
                var t = e && e.toString();
                return !ge.isArray(e) && t - parseFloat(t) + 1 >= 0
            }, isEmptyObject: function (e) {
                var t;
                for (t in e)return !1;
                return !0
            }, isPlainObject: function (e) {
                var t;
                if (!e || "object" !== ge.type(e) || e.nodeType || ge.isWindow(e))return !1;
                try {
                    if (e.constructor && !he.call(e, "constructor") && !he.call(e.constructor.prototype, "isPrototypeOf"))return !1
                } catch (e) {
                    return !1
                }
                if (!me.ownFirst)for (t in e)return he.call(e, t);
                for (t in e);
                return void 0 === t || he.call(e, t)
            }, type: function (e) {
                return null == e ? e + "" : "object" == typeof e || "function" == typeof e ? pe[fe.call(e)] || "object" : typeof e
            }, globalEval: function (e) {
                e && ge.trim(e) && (n.execScript || function (e) {
                    n.eval.call(n, e)
                })(e)
            }, camelCase: function (e) {
                return e.replace(be, "ms-").replace(xe, we)
            }, nodeName: function (e, t) {
                return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
            }, each: function (e, t) {
                var n, r = 0;
                if (a(e))for (n = e.length; r < n && t.call(e[r], r, e[r]) !== !1; r++); else for (r in e)if (t.call(e[r], r, e[r]) === !1)break;
                return e
            }, trim: function (e) {
                return null == e ? "" : (e + "").replace(ye, "")
            }, makeArray: function (e, t) {
                var n = t || [];
                return null != e && (a(Object(e)) ? ge.merge(n, "string" == typeof e ? [e] : e) : ce.call(n, e)), n
            }, inArray: function (e, t, n) {
                var r;
                if (t) {
                    if (de)return de.call(t, e, n);
                    for (r = t.length, n = n ? n < 0 ? Math.max(0, r + n) : n : 0; n < r; n++)if (n in t && t[n] === e)return n
                }
                return -1
            }, merge: function (e, t) {
                for (var n = +t.length, r = 0, i = e.length; r < n;)e[i++] = t[r++];
                if (n !== n)for (; void 0 !== t[r];)e[i++] = t[r++];
                return e.length = i, e
            }, grep: function (e, t, n) {
                for (var r, i = [], o = 0, a = e.length, s = !n; o < a; o++)r = !t(e[o], o), r !== s && i.push(e[o]);
                return i
            }, map: function (e, t, n) {
                var r, i, o = 0, s = [];
                if (a(e))for (r = e.length; o < r; o++)i = t(e[o], o, n), null != i && s.push(i); else for (o in e)i = t(e[o], o, n), null != i && s.push(i);
                return ue.apply([], s)
            }, guid: 1, proxy: function (e, t) {
                var n, r, i;
                if ("string" == typeof t && (i = e[t], t = e, e = i), ge.isFunction(e))return n = le.call(arguments, 2), r = function () {
                    return e.apply(t || this, n.concat(le.call(arguments)))
                }, r.guid = e.guid = e.guid || ge.guid++, r
            }, now: function () {
                return +new Date
            }, support: me
        }), "function" == typeof Symbol && (ge.fn[Symbol.iterator] = ae[Symbol.iterator]), ge.each("Boolean Number String Function Array Date RegExp Object Error Symbol".split(" "), function (e, t) {
            pe["[object " + t + "]"] = t.toLowerCase()
        });
        var Ce = /*!
         * Sizzle CSS Selector Engine v2.2.1
         * http://sizzlejs.com/
         *
         * Copyright jQuery Foundation and other contributors
         * Released under the MIT license
         * http://jquery.org/license
         *
         * Date: 2015-10-17
         */
            function (e) {
                function t(e, t, n, r) {
                    var i, o, a, s, l, u, d, f, h = t && t.ownerDocument, m = t ? t.nodeType : 9;
                    if (n = n || [], "string" != typeof e || !e || 1 !== m && 9 !== m && 11 !== m)return n;
                    if (!r && ((t ? t.ownerDocument || t : B) !== j && L(t), t = t || j, H)) {
                        if (11 !== m && (u = ge.exec(e)))if (i = u[1]) {
                            if (9 === m) {
                                if (!(a = t.getElementById(i)))return n;
                                if (a.id === i)return n.push(a), n
                            } else if (h && (a = h.getElementById(i)) && I(t, a) && a.id === i)return n.push(a), n
                        } else {
                            if (u[2])return Z.apply(n, t.getElementsByTagName(e)), n;
                            if ((i = u[3]) && w.getElementsByClassName && t.getElementsByClassName)return Z.apply(n, t.getElementsByClassName(i)), n
                        }
                        if (w.qsa && !X[e + " "] && (!q || !q.test(e))) {
                            if (1 !== m) h = t, f = e; else if ("object" !== t.nodeName.toLowerCase()) {
                                for ((s = t.getAttribute("id")) ? s = s.replace(be, "\\$&") : t.setAttribute("id", s = F), d = E(e), o = d.length, l = pe.test(s) ? "#" + s : "[id='" + s + "']"; o--;)d[o] = l + " " + p(d[o]);
                                f = d.join(","), h = ye.test(e) && c(t.parentNode) || t
                            }
                            if (f)try {
                                return Z.apply(n, h.querySelectorAll(f)), n
                            } catch (e) {
                            } finally {
                                s === F && t.removeAttribute("id")
                            }
                        }
                    }
                    return S(e.replace(se, "$1"), t, n, r)
                }

                function n() {
                    function e(n, r) {
                        return t.push(n + " ") > C.cacheLength && delete e[t.shift()], e[n + " "] = r
                    }

                    var t = [];
                    return e
                }

                function r(e) {
                    return e[F] = !0, e
                }

                function i(e) {
                    var t = j.createElement("div");
                    try {
                        return !!e(t)
                    } catch (e) {
                        return !1
                    } finally {
                        t.parentNode && t.parentNode.removeChild(t), t = null
                    }
                }

                function o(e, t) {
                    for (var n = e.split("|"), r = n.length; r--;)C.attrHandle[n[r]] = t
                }

                function a(e, t) {
                    var n = t && e,
                        r = n && 1 === e.nodeType && 1 === t.nodeType && (~t.sourceIndex || G) - (~e.sourceIndex || G);
                    if (r)return r;
                    if (n)for (; n = n.nextSibling;)if (n === t)return -1;
                    return e ? 1 : -1
                }

                function s(e) {
                    return function (t) {
                        var n = t.nodeName.toLowerCase();
                        return "input" === n && t.type === e
                    }
                }

                function l(e) {
                    return function (t) {
                        var n = t.nodeName.toLowerCase();
                        return ("input" === n || "button" === n) && t.type === e
                    }
                }

                function u(e) {
                    return r(function (t) {
                        return t = +t, r(function (n, r) {
                            for (var i, o = e([], n.length, t), a = o.length; a--;)n[i = o[a]] && (n[i] = !(r[i] = n[i]))
                        })
                    })
                }

                function c(e) {
                    return e && "undefined" != typeof e.getElementsByTagName && e
                }

                function d() {
                }

                function p(e) {
                    for (var t = 0, n = e.length, r = ""; t < n; t++)r += e[t].value;
                    return r
                }

                function f(e, t, n) {
                    var r = t.dir, i = n && "parentNode" === r, o = W++;
                    return t.first ? function (t, n, o) {
                        for (; t = t[r];)if (1 === t.nodeType || i)return e(t, n, o)
                    } : function (t, n, a) {
                        var s, l, u, c = [R, o];
                        if (a) {
                            for (; t = t[r];)if ((1 === t.nodeType || i) && e(t, n, a))return !0
                        } else for (; t = t[r];)if (1 === t.nodeType || i) {
                            if (u = t[F] || (t[F] = {}), l = u[t.uniqueID] || (u[t.uniqueID] = {}), (s = l[r]) && s[0] === R && s[1] === o)return c[2] = s[2];
                            if (l[r] = c, c[2] = e(t, n, a))return !0
                        }
                    }
                }

                function h(e) {
                    return e.length > 1 ? function (t, n, r) {
                        for (var i = e.length; i--;)if (!e[i](t, n, r))return !1;
                        return !0
                    } : e[0]
                }

                function m(e, n, r) {
                    for (var i = 0, o = n.length; i < o; i++)t(e, n[i], r);
                    return r
                }

                function v(e, t, n, r, i) {
                    for (var o, a = [], s = 0, l = e.length, u = null != t; s < l; s++)(o = e[s]) && (n && !n(o, r, i) || (a.push(o), u && t.push(s)));
                    return a
                }

                function g(e, t, n, i, o, a) {
                    return i && !i[F] && (i = g(i)), o && !o[F] && (o = g(o, a)), r(function (r, a, s, l) {
                        var u, c, d, p = [], f = [], h = a.length, g = r || m(t || "*", s.nodeType ? [s] : s, []),
                            y = !e || !r && t ? g : v(g, p, e, s, l), b = n ? o || (r ? e : h || i) ? [] : a : y;
                        if (n && n(y, b, s, l), i)for (u = v(b, f), i(u, [], s, l), c = u.length; c--;)(d = u[c]) && (b[f[c]] = !(y[f[c]] = d));
                        if (r) {
                            if (o || e) {
                                if (o) {
                                    for (u = [], c = b.length; c--;)(d = b[c]) && u.push(y[c] = d);
                                    o(null, b = [], u, l)
                                }
                                for (c = b.length; c--;)(d = b[c]) && (u = o ? ee(r, d) : p[c]) > -1 && (r[u] = !(a[u] = d))
                            }
                        } else b = v(b === a ? b.splice(h, b.length) : b), o ? o(null, a, b, l) : Z.apply(a, b)
                    })
                }

                function y(e) {
                    for (var t, n, r, i = e.length, o = C.relative[e[0].type], a = o || C.relative[" "], s = o ? 1 : 0, l = f(function (e) {
                        return e === t
                    }, a, !0), u = f(function (e) {
                        return ee(t, e) > -1
                    }, a, !0), c = [function (e, n, r) {
                        var i = !o && (r || n !== A) || ((t = n).nodeType ? l(e, n, r) : u(e, n, r));
                        return t = null, i
                    }]; s < i; s++)if (n = C.relative[e[s].type]) c = [f(h(c), n)]; else {
                        if (n = C.filter[e[s].type].apply(null, e[s].matches), n[F]) {
                            for (r = ++s; r < i && !C.relative[e[r].type]; r++);
                            return g(s > 1 && h(c), s > 1 && p(e.slice(0, s - 1).concat({value: " " === e[s - 2].type ? "*" : ""})).replace(se, "$1"), n, s < r && y(e.slice(s, r)), r < i && y(e = e.slice(r)), r < i && p(e))
                        }
                        c.push(n)
                    }
                    return h(c)
                }

                function b(e, n) {
                    var i = n.length > 0, o = e.length > 0, a = function (r, a, s, l, u) {
                        var c, d, p, f = 0, h = "0", m = r && [], g = [], y = A, b = r || o && C.find.TAG("*", u),
                            x = R += null == y ? 1 : Math.random() || .1, w = b.length;
                        for (u && (A = a === j || a || u); h !== w && null != (c = b[h]); h++) {
                            if (o && c) {
                                for (d = 0, a || c.ownerDocument === j || (L(c), s = !H); p = e[d++];)if (p(c, a || j, s)) {
                                    l.push(c);
                                    break
                                }
                                u && (R = x)
                            }
                            i && ((c = !p && c) && f--, r && m.push(c))
                        }
                        if (f += h, i && h !== f) {
                            for (d = 0; p = n[d++];)p(m, g, a, s);
                            if (r) {
                                if (f > 0)for (; h--;)m[h] || g[h] || (g[h] = J.call(l));
                                g = v(g)
                            }
                            Z.apply(l, g), u && !r && g.length > 0 && f + n.length > 1 && t.uniqueSort(l)
                        }
                        return u && (R = x, A = y), m
                    };
                    return i ? r(a) : a
                }

                var x, w, C, T, k, E, N, S, A, P, D, L, j, _, H, q, M, O, I, F = "sizzle" + 1 * new Date,
                    B = e.document, R = 0, W = 0, z = n(), $ = n(), X = n(), V = function (e, t) {
                        return e === t && (D = !0), 0
                    }, G = 1 << 31, U = {}.hasOwnProperty, Q = [], J = Q.pop, Y = Q.push, Z = Q.push, K = Q.slice,
                    ee = function (e, t) {
                        for (var n = 0, r = e.length; n < r; n++)if (e[n] === t)return n;
                        return -1
                    },
                    te = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",
                    ne = "[\\x20\\t\\r\\n\\f]", re = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+",
                    ie = "\\[" + ne + "*(" + re + ")(?:" + ne + "*([*^$|!~]?=)" + ne + "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + re + "))|)" + ne + "*\\]",
                    oe = ":(" + re + ")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|" + ie + ")*)|.*)\\)|)",
                    ae = new RegExp(ne + "+", "g"),
                    se = new RegExp("^" + ne + "+|((?:^|[^\\\\])(?:\\\\.)*)" + ne + "+$", "g"),
                    le = new RegExp("^" + ne + "*," + ne + "*"),
                    ue = new RegExp("^" + ne + "*([>+~]|" + ne + ")" + ne + "*"),
                    ce = new RegExp("=" + ne + "*([^\\]'\"]*?)" + ne + "*\\]", "g"), de = new RegExp(oe),
                    pe = new RegExp("^" + re + "$"), fe = {
                        ID: new RegExp("^#(" + re + ")"),
                        CLASS: new RegExp("^\\.(" + re + ")"),
                        TAG: new RegExp("^(" + re + "|[*])"),
                        ATTR: new RegExp("^" + ie),
                        PSEUDO: new RegExp("^" + oe),
                        CHILD: new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + ne + "*(even|odd|(([+-]|)(\\d*)n|)" + ne + "*(?:([+-]|)" + ne + "*(\\d+)|))" + ne + "*\\)|)", "i"),
                        bool: new RegExp("^(?:" + te + ")$", "i"),
                        needsContext: new RegExp("^" + ne + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + ne + "*((?:-\\d)?\\d*)" + ne + "*\\)|)(?=[^-]|$)", "i")
                    }, he = /^(?:input|select|textarea|button)$/i, me = /^h\d$/i, ve = /^[^{]+\{\s*\[native \w/,
                    ge = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/, ye = /[+~]/, be = /'|\\/g,
                    xe = new RegExp("\\\\([\\da-f]{1,6}" + ne + "?|(" + ne + ")|.)", "ig"), we = function (e, t, n) {
                        var r = "0x" + t - 65536;
                        return r !== r || n ? t : r < 0 ? String.fromCharCode(r + 65536) : String.fromCharCode(r >> 10 | 55296, 1023 & r | 56320)
                    }, Ce = function () {
                        L()
                    };
                try {
                    Z.apply(Q = K.call(B.childNodes), B.childNodes), Q[B.childNodes.length].nodeType
                } catch (e) {
                    Z = {
                        apply: Q.length ? function (e, t) {
                            Y.apply(e, K.call(t))
                        } : function (e, t) {
                            for (var n = e.length, r = 0; e[n++] = t[r++];);
                            e.length = n - 1
                        }
                    }
                }
                w = t.support = {}, k = t.isXML = function (e) {
                    var t = e && (e.ownerDocument || e).documentElement;
                    return !!t && "HTML" !== t.nodeName
                }, L = t.setDocument = function (e) {
                    var t, n, r = e ? e.ownerDocument || e : B;
                    return r !== j && 9 === r.nodeType && r.documentElement ? (j = r, _ = j.documentElement, H = !k(j), (n = j.defaultView) && n.top !== n && (n.addEventListener ? n.addEventListener("unload", Ce, !1) : n.attachEvent && n.attachEvent("onunload", Ce)), w.attributes = i(function (e) {
                        return e.className = "i", !e.getAttribute("className")
                    }), w.getElementsByTagName = i(function (e) {
                        return e.appendChild(j.createComment("")), !e.getElementsByTagName("*").length
                    }), w.getElementsByClassName = ve.test(j.getElementsByClassName), w.getById = i(function (e) {
                        return _.appendChild(e).id = F, !j.getElementsByName || !j.getElementsByName(F).length
                    }), w.getById ? (C.find.ID = function (e, t) {
                        if ("undefined" != typeof t.getElementById && H) {
                            var n = t.getElementById(e);
                            return n ? [n] : []
                        }
                    }, C.filter.ID = function (e) {
                        var t = e.replace(xe, we);
                        return function (e) {
                            return e.getAttribute("id") === t
                        }
                    }) : (delete C.find.ID, C.filter.ID = function (e) {
                        var t = e.replace(xe, we);
                        return function (e) {
                            var n = "undefined" != typeof e.getAttributeNode && e.getAttributeNode("id");
                            return n && n.value === t
                        }
                    }), C.find.TAG = w.getElementsByTagName ? function (e, t) {
                        return "undefined" != typeof t.getElementsByTagName ? t.getElementsByTagName(e) : w.qsa ? t.querySelectorAll(e) : void 0
                    } : function (e, t) {
                        var n, r = [], i = 0, o = t.getElementsByTagName(e);
                        if ("*" === e) {
                            for (; n = o[i++];)1 === n.nodeType && r.push(n);
                            return r
                        }
                        return o
                    }, C.find.CLASS = w.getElementsByClassName && function (e, t) {
                            if ("undefined" != typeof t.getElementsByClassName && H)return t.getElementsByClassName(e)
                        }, M = [], q = [], (w.qsa = ve.test(j.querySelectorAll)) && (i(function (e) {
                        _.appendChild(e).innerHTML = "<a id='" + F + "'></a><select id='" + F + "-\r\\' msallowcapture=''><option selected=''></option></select>", e.querySelectorAll("[msallowcapture^='']").length && q.push("[*^$]=" + ne + "*(?:''|\"\")"), e.querySelectorAll("[selected]").length || q.push("\\[" + ne + "*(?:value|" + te + ")"), e.querySelectorAll("[id~=" + F + "-]").length || q.push("~="), e.querySelectorAll(":checked").length || q.push(":checked"), e.querySelectorAll("a#" + F + "+*").length || q.push(".#.+[+~]")
                    }), i(function (e) {
                        var t = j.createElement("input");
                        t.setAttribute("type", "hidden"), e.appendChild(t).setAttribute("name", "D"), e.querySelectorAll("[name=d]").length && q.push("name" + ne + "*[*^$|!~]?="), e.querySelectorAll(":enabled").length || q.push(":enabled", ":disabled"), e.querySelectorAll("*,:x"), q.push(",.*:")
                    })), (w.matchesSelector = ve.test(O = _.matches || _.webkitMatchesSelector || _.mozMatchesSelector || _.oMatchesSelector || _.msMatchesSelector)) && i(function (e) {
                        w.disconnectedMatch = O.call(e, "div"), O.call(e, "[s!='']:x"), M.push("!=", oe)
                    }), q = q.length && new RegExp(q.join("|")), M = M.length && new RegExp(M.join("|")), t = ve.test(_.compareDocumentPosition), I = t || ve.test(_.contains) ? function (e, t) {
                        var n = 9 === e.nodeType ? e.documentElement : e, r = t && t.parentNode;
                        return e === r || !(!r || 1 !== r.nodeType || !(n.contains ? n.contains(r) : e.compareDocumentPosition && 16 & e.compareDocumentPosition(r)))
                    } : function (e, t) {
                        if (t)for (; t = t.parentNode;)if (t === e)return !0;
                        return !1
                    }, V = t ? function (e, t) {
                        if (e === t)return D = !0, 0;
                        var n = !e.compareDocumentPosition - !t.compareDocumentPosition;
                        return n ? n : (n = (e.ownerDocument || e) === (t.ownerDocument || t) ? e.compareDocumentPosition(t) : 1, 1 & n || !w.sortDetached && t.compareDocumentPosition(e) === n ? e === j || e.ownerDocument === B && I(B, e) ? -1 : t === j || t.ownerDocument === B && I(B, t) ? 1 : P ? ee(P, e) - ee(P, t) : 0 : 4 & n ? -1 : 1)
                    } : function (e, t) {
                        if (e === t)return D = !0, 0;
                        var n, r = 0, i = e.parentNode, o = t.parentNode, s = [e], l = [t];
                        if (!i || !o)return e === j ? -1 : t === j ? 1 : i ? -1 : o ? 1 : P ? ee(P, e) - ee(P, t) : 0;
                        if (i === o)return a(e, t);
                        for (n = e; n = n.parentNode;)s.unshift(n);
                        for (n = t; n = n.parentNode;)l.unshift(n);
                        for (; s[r] === l[r];)r++;
                        return r ? a(s[r], l[r]) : s[r] === B ? -1 : l[r] === B ? 1 : 0
                    }, j) : j
                }, t.matches = function (e, n) {
                    return t(e, null, null, n)
                }, t.matchesSelector = function (e, n) {
                    if ((e.ownerDocument || e) !== j && L(e), n = n.replace(ce, "='$1']"), w.matchesSelector && H && !X[n + " "] && (!M || !M.test(n)) && (!q || !q.test(n)))try {
                        var r = O.call(e, n);
                        if (r || w.disconnectedMatch || e.document && 11 !== e.document.nodeType)return r
                    } catch (e) {
                    }
                    return t(n, j, null, [e]).length > 0
                }, t.contains = function (e, t) {
                    return (e.ownerDocument || e) !== j && L(e), I(e, t)
                }, t.attr = function (e, t) {
                    (e.ownerDocument || e) !== j && L(e);
                    var n = C.attrHandle[t.toLowerCase()],
                        r = n && U.call(C.attrHandle, t.toLowerCase()) ? n(e, t, !H) : void 0;
                    return void 0 !== r ? r : w.attributes || !H ? e.getAttribute(t) : (r = e.getAttributeNode(t)) && r.specified ? r.value : null
                }, t.error = function (e) {
                    throw new Error("Syntax error, unrecognized expression: " + e)
                }, t.uniqueSort = function (e) {
                    var t, n = [], r = 0, i = 0;
                    if (D = !w.detectDuplicates, P = !w.sortStable && e.slice(0), e.sort(V), D) {
                        for (; t = e[i++];)t === e[i] && (r = n.push(i));
                        for (; r--;)e.splice(n[r], 1)
                    }
                    return P = null, e
                }, T = t.getText = function (e) {
                    var t, n = "", r = 0, i = e.nodeType;
                    if (i) {
                        if (1 === i || 9 === i || 11 === i) {
                            if ("string" == typeof e.textContent)return e.textContent;
                            for (e = e.firstChild; e; e = e.nextSibling)n += T(e)
                        } else if (3 === i || 4 === i)return e.nodeValue
                    } else for (; t = e[r++];)n += T(t);
                    return n
                }, C = t.selectors = {
                    cacheLength: 50,
                    createPseudo: r,
                    match: fe,
                    attrHandle: {},
                    find: {},
                    relative: {
                        ">": {dir: "parentNode", first: !0},
                        " ": {dir: "parentNode"},
                        "+": {dir: "previousSibling", first: !0},
                        "~": {dir: "previousSibling"}
                    },
                    preFilter: {
                        ATTR: function (e) {
                            return e[1] = e[1].replace(xe, we), e[3] = (e[3] || e[4] || e[5] || "").replace(xe, we), "~=" === e[2] && (e[3] = " " + e[3] + " "), e.slice(0, 4)
                        }, CHILD: function (e) {
                            return e[1] = e[1].toLowerCase(), "nth" === e[1].slice(0, 3) ? (e[3] || t.error(e[0]), e[4] = +(e[4] ? e[5] + (e[6] || 1) : 2 * ("even" === e[3] || "odd" === e[3])), e[5] = +(e[7] + e[8] || "odd" === e[3])) : e[3] && t.error(e[0]), e
                        }, PSEUDO: function (e) {
                            var t, n = !e[6] && e[2];
                            return fe.CHILD.test(e[0]) ? null : (e[3] ? e[2] = e[4] || e[5] || "" : n && de.test(n) && (t = E(n, !0)) && (t = n.indexOf(")", n.length - t) - n.length) && (e[0] = e[0].slice(0, t), e[2] = n.slice(0, t)), e.slice(0, 3))
                        }
                    },
                    filter: {
                        TAG: function (e) {
                            var t = e.replace(xe, we).toLowerCase();
                            return "*" === e ? function () {
                                return !0
                            } : function (e) {
                                return e.nodeName && e.nodeName.toLowerCase() === t
                            }
                        }, CLASS: function (e) {
                            var t = z[e + " "];
                            return t || (t = new RegExp("(^|" + ne + ")" + e + "(" + ne + "|$)")) && z(e, function (e) {
                                    return t.test("string" == typeof e.className && e.className || "undefined" != typeof e.getAttribute && e.getAttribute("class") || "")
                                })
                        }, ATTR: function (e, n, r) {
                            return function (i) {
                                var o = t.attr(i, e);
                                return null == o ? "!=" === n : !n || (o += "", "=" === n ? o === r : "!=" === n ? o !== r : "^=" === n ? r && 0 === o.indexOf(r) : "*=" === n ? r && o.indexOf(r) > -1 : "$=" === n ? r && o.slice(-r.length) === r : "~=" === n ? (" " + o.replace(ae, " ") + " ").indexOf(r) > -1 : "|=" === n && (o === r || o.slice(0, r.length + 1) === r + "-"))
                            }
                        }, CHILD: function (e, t, n, r, i) {
                            var o = "nth" !== e.slice(0, 3), a = "last" !== e.slice(-4), s = "of-type" === t;
                            return 1 === r && 0 === i ? function (e) {
                                return !!e.parentNode
                            } : function (t, n, l) {
                                var u, c, d, p, f, h, m = o !== a ? "nextSibling" : "previousSibling", v = t.parentNode,
                                    g = s && t.nodeName.toLowerCase(), y = !l && !s, b = !1;
                                if (v) {
                                    if (o) {
                                        for (; m;) {
                                            for (p = t; p = p[m];)if (s ? p.nodeName.toLowerCase() === g : 1 === p.nodeType)return !1;
                                            h = m = "only" === e && !h && "nextSibling"
                                        }
                                        return !0
                                    }
                                    if (h = [a ? v.firstChild : v.lastChild], a && y) {
                                        for (p = v, d = p[F] || (p[F] = {}), c = d[p.uniqueID] || (d[p.uniqueID] = {}), u = c[e] || [], f = u[0] === R && u[1], b = f && u[2], p = f && v.childNodes[f]; p = ++f && p && p[m] || (b = f = 0) || h.pop();)if (1 === p.nodeType && ++b && p === t) {
                                            c[e] = [R, f, b];
                                            break
                                        }
                                    } else if (y && (p = t, d = p[F] || (p[F] = {}), c = d[p.uniqueID] || (d[p.uniqueID] = {}), u = c[e] || [], f = u[0] === R && u[1], b = f), b === !1)for (; (p = ++f && p && p[m] || (b = f = 0) || h.pop()) && ((s ? p.nodeName.toLowerCase() !== g : 1 !== p.nodeType) || !++b || (y && (d = p[F] || (p[F] = {}), c = d[p.uniqueID] || (d[p.uniqueID] = {}), c[e] = [R, b]), p !== t)););
                                    return b -= i, b === r || b % r === 0 && b / r >= 0
                                }
                            }
                        }, PSEUDO: function (e, n) {
                            var i,
                                o = C.pseudos[e] || C.setFilters[e.toLowerCase()] || t.error("unsupported pseudo: " + e);
                            return o[F] ? o(n) : o.length > 1 ? (i = [e, e, "", n], C.setFilters.hasOwnProperty(e.toLowerCase()) ? r(function (e, t) {
                                for (var r, i = o(e, n), a = i.length; a--;)r = ee(e, i[a]), e[r] = !(t[r] = i[a])
                            }) : function (e) {
                                return o(e, 0, i)
                            }) : o
                        }
                    },
                    pseudos: {
                        not: r(function (e) {
                            var t = [], n = [], i = N(e.replace(se, "$1"));
                            return i[F] ? r(function (e, t, n, r) {
                                for (var o, a = i(e, null, r, []), s = e.length; s--;)(o = a[s]) && (e[s] = !(t[s] = o))
                            }) : function (e, r, o) {
                                return t[0] = e, i(t, null, o, n), t[0] = null, !n.pop()
                            }
                        }), has: r(function (e) {
                            return function (n) {
                                return t(e, n).length > 0
                            }
                        }), contains: r(function (e) {
                            return e = e.replace(xe, we), function (t) {
                                return (t.textContent || t.innerText || T(t)).indexOf(e) > -1
                            }
                        }), lang: r(function (e) {
                            return pe.test(e || "") || t.error("unsupported lang: " + e), e = e.replace(xe, we).toLowerCase(), function (t) {
                                var n;
                                do if (n = H ? t.lang : t.getAttribute("xml:lang") || t.getAttribute("lang"))return n = n.toLowerCase(), n === e || 0 === n.indexOf(e + "-"); while ((t = t.parentNode) && 1 === t.nodeType);
                                return !1
                            }
                        }), target: function (t) {
                            var n = e.location && e.location.hash;
                            return n && n.slice(1) === t.id
                        }, root: function (e) {
                            return e === _
                        }, focus: function (e) {
                            return e === j.activeElement && (!j.hasFocus || j.hasFocus()) && !!(e.type || e.href || ~e.tabIndex)
                        }, enabled: function (e) {
                            return e.disabled === !1
                        }, disabled: function (e) {
                            return e.disabled === !0
                        }, checked: function (e) {
                            var t = e.nodeName.toLowerCase();
                            return "input" === t && !!e.checked || "option" === t && !!e.selected
                        }, selected: function (e) {
                            return e.parentNode && e.parentNode.selectedIndex, e.selected === !0
                        }, empty: function (e) {
                            for (e = e.firstChild; e; e = e.nextSibling)if (e.nodeType < 6)return !1;
                            return !0
                        }, parent: function (e) {
                            return !C.pseudos.empty(e)
                        }, header: function (e) {
                            return me.test(e.nodeName)
                        }, input: function (e) {
                            return he.test(e.nodeName)
                        }, button: function (e) {
                            var t = e.nodeName.toLowerCase();
                            return "input" === t && "button" === e.type || "button" === t
                        }, text: function (e) {
                            var t;
                            return "input" === e.nodeName.toLowerCase() && "text" === e.type && (null == (t = e.getAttribute("type")) || "text" === t.toLowerCase())
                        }, first: u(function () {
                            return [0]
                        }), last: u(function (e, t) {
                            return [t - 1]
                        }), eq: u(function (e, t, n) {
                            return [n < 0 ? n + t : n]
                        }), even: u(function (e, t) {
                            for (var n = 0; n < t; n += 2)e.push(n);
                            return e
                        }), odd: u(function (e, t) {
                            for (var n = 1; n < t; n += 2)e.push(n);
                            return e
                        }), lt: u(function (e, t, n) {
                            for (var r = n < 0 ? n + t : n; --r >= 0;)e.push(r);
                            return e
                        }), gt: u(function (e, t, n) {
                            for (var r = n < 0 ? n + t : n; ++r < t;)e.push(r);
                            return e
                        })
                    }
                }, C.pseudos.nth = C.pseudos.eq;
                for (x in{radio: !0, checkbox: !0, file: !0, password: !0, image: !0})C.pseudos[x] = s(x);
                for (x in{submit: !0, reset: !0})C.pseudos[x] = l(x);
                return d.prototype = C.filters = C.pseudos, C.setFilters = new d, E = t.tokenize = function (e, n) {
                    var r, i, o, a, s, l, u, c = $[e + " "];
                    if (c)return n ? 0 : c.slice(0);
                    for (s = e, l = [], u = C.preFilter; s;) {
                        r && !(i = le.exec(s)) || (i && (s = s.slice(i[0].length) || s), l.push(o = [])), r = !1, (i = ue.exec(s)) && (r = i.shift(), o.push({
                            value: r,
                            type: i[0].replace(se, " ")
                        }), s = s.slice(r.length));
                        for (a in C.filter)!(i = fe[a].exec(s)) || u[a] && !(i = u[a](i)) || (r = i.shift(), o.push({
                            value: r,
                            type: a,
                            matches: i
                        }), s = s.slice(r.length));
                        if (!r)break
                    }
                    return n ? s.length : s ? t.error(e) : $(e, l).slice(0)
                }, N = t.compile = function (e, t) {
                    var n, r = [], i = [], o = X[e + " "];
                    if (!o) {
                        for (t || (t = E(e)), n = t.length; n--;)o = y(t[n]), o[F] ? r.push(o) : i.push(o);
                        o = X(e, b(i, r)), o.selector = e
                    }
                    return o
                }, S = t.select = function (e, t, n, r) {
                    var i, o, a, s, l, u = "function" == typeof e && e, d = !r && E(e = u.selector || e);
                    if (n = n || [], 1 === d.length) {
                        if (o = d[0] = d[0].slice(0), o.length > 2 && "ID" === (a = o[0]).type && w.getById && 9 === t.nodeType && H && C.relative[o[1].type]) {
                            if (t = (C.find.ID(a.matches[0].replace(xe, we), t) || [])[0], !t)return n;
                            u && (t = t.parentNode), e = e.slice(o.shift().value.length)
                        }
                        for (i = fe.needsContext.test(e) ? 0 : o.length; i-- && (a = o[i], !C.relative[s = a.type]);)if ((l = C.find[s]) && (r = l(a.matches[0].replace(xe, we), ye.test(o[0].type) && c(t.parentNode) || t))) {
                            if (o.splice(i, 1), e = r.length && p(o), !e)return Z.apply(n, r), n;
                            break
                        }
                    }
                    return (u || N(e, d))(r, t, !H, n, !t || ye.test(e) && c(t.parentNode) || t), n
                }, w.sortStable = F.split("").sort(V).join("") === F, w.detectDuplicates = !!D, L(), w.sortDetached = i(function (e) {
                    return 1 & e.compareDocumentPosition(j.createElement("div"))
                }), i(function (e) {
                    return e.innerHTML = "<a href='#'></a>", "#" === e.firstChild.getAttribute("href")
                }) || o("type|href|height|width", function (e, t, n) {
                    if (!n)return e.getAttribute(t, "type" === t.toLowerCase() ? 1 : 2)
                }), w.attributes && i(function (e) {
                    return e.innerHTML = "<input/>", e.firstChild.setAttribute("value", ""), "" === e.firstChild.getAttribute("value")
                }) || o("value", function (e, t, n) {
                    if (!n && "input" === e.nodeName.toLowerCase())return e.defaultValue
                }), i(function (e) {
                    return null == e.getAttribute("disabled")
                }) || o(te, function (e, t, n) {
                    var r;
                    if (!n)return e[t] === !0 ? t.toLowerCase() : (r = e.getAttributeNode(t)) && r.specified ? r.value : null
                }), t
            }(n);
        ge.find = Ce, ge.expr = Ce.selectors, ge.expr[":"] = ge.expr.pseudos, ge.uniqueSort = ge.unique = Ce.uniqueSort, ge.text = Ce.getText, ge.isXMLDoc = Ce.isXML, ge.contains = Ce.contains;
        var Te = function (e, t, n) {
            for (var r = [], i = void 0 !== n; (e = e[t]) && 9 !== e.nodeType;)if (1 === e.nodeType) {
                if (i && ge(e).is(n))break;
                r.push(e)
            }
            return r
        }, ke = function (e, t) {
            for (var n = []; e; e = e.nextSibling)1 === e.nodeType && e !== t && n.push(e);
            return n
        }, Ee = ge.expr.match.needsContext, Ne = /^<([\w-]+)\s*\/?>(?:<\/\1>|)$/, Se = /^.[^:#\[\.,]*$/;
        ge.filter = function (e, t, n) {
            var r = t[0];
            return n && (e = ":not(" + e + ")"), 1 === t.length && 1 === r.nodeType ? ge.find.matchesSelector(r, e) ? [r] : [] : ge.find.matches(e, ge.grep(t, function (e) {
                return 1 === e.nodeType
            }))
        }, ge.fn.extend({
            find: function (e) {
                var t, n = [], r = this, i = r.length;
                if ("string" != typeof e)return this.pushStack(ge(e).filter(function () {
                    for (t = 0; t < i; t++)if (ge.contains(r[t], this))return !0
                }));
                for (t = 0; t < i; t++)ge.find(e, r[t], n);
                return n = this.pushStack(i > 1 ? ge.unique(n) : n), n.selector = this.selector ? this.selector + " " + e : e, n
            }, filter: function (e) {
                return this.pushStack(s(this, e || [], !1))
            }, not: function (e) {
                return this.pushStack(s(this, e || [], !0))
            }, is: function (e) {
                return !!s(this, "string" == typeof e && Ee.test(e) ? ge(e) : e || [], !1).length
            }
        });
        var Ae, Pe = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/, De = ge.fn.init = function (e, t, n) {
            var r, i;
            if (!e)return this;
            if (n = n || Ae, "string" == typeof e) {
                if (r = "<" === e.charAt(0) && ">" === e.charAt(e.length - 1) && e.length >= 3 ? [null, e, null] : Pe.exec(e), !r || !r[1] && t)return !t || t.jquery ? (t || n).find(e) : this.constructor(t).find(e);
                if (r[1]) {
                    if (t = t instanceof ge ? t[0] : t, ge.merge(this, ge.parseHTML(r[1], t && t.nodeType ? t.ownerDocument || t : se, !0)), Ne.test(r[1]) && ge.isPlainObject(t))for (r in t)ge.isFunction(this[r]) ? this[r](t[r]) : this.attr(r, t[r]);
                    return this
                }
                if (i = se.getElementById(r[2]), i && i.parentNode) {
                    if (i.id !== r[2])return Ae.find(e);
                    this.length = 1, this[0] = i
                }
                return this.context = se, this.selector = e, this
            }
            return e.nodeType ? (this.context = this[0] = e, this.length = 1, this) : ge.isFunction(e) ? "undefined" != typeof n.ready ? n.ready(e) : e(ge) : (void 0 !== e.selector && (this.selector = e.selector, this.context = e.context), ge.makeArray(e, this))
        };
        De.prototype = ge.fn, Ae = ge(se);
        var Le = /^(?:parents|prev(?:Until|All))/, je = {children: !0, contents: !0, next: !0, prev: !0};
        ge.fn.extend({
            has: function (e) {
                var t, n = ge(e, this), r = n.length;
                return this.filter(function () {
                    for (t = 0; t < r; t++)if (ge.contains(this, n[t]))return !0
                })
            }, closest: function (e, t) {
                for (var n, r = 0, i = this.length, o = [], a = Ee.test(e) || "string" != typeof e ? ge(e, t || this.context) : 0; r < i; r++)for (n = this[r]; n && n !== t; n = n.parentNode)if (n.nodeType < 11 && (a ? a.index(n) > -1 : 1 === n.nodeType && ge.find.matchesSelector(n, e))) {
                    o.push(n);
                    break
                }
                return this.pushStack(o.length > 1 ? ge.uniqueSort(o) : o)
            }, index: function (e) {
                return e ? "string" == typeof e ? ge.inArray(this[0], ge(e)) : ge.inArray(e.jquery ? e[0] : e, this) : this[0] && this[0].parentNode ? this.first().prevAll().length : -1
            }, add: function (e, t) {
                return this.pushStack(ge.uniqueSort(ge.merge(this.get(), ge(e, t))))
            }, addBack: function (e) {
                return this.add(null == e ? this.prevObject : this.prevObject.filter(e))
            }
        }), ge.each({
            parent: function (e) {
                var t = e.parentNode;
                return t && 11 !== t.nodeType ? t : null
            }, parents: function (e) {
                return Te(e, "parentNode")
            }, parentsUntil: function (e, t, n) {
                return Te(e, "parentNode", n)
            }, next: function (e) {
                return l(e, "nextSibling")
            }, prev: function (e) {
                return l(e, "previousSibling")
            }, nextAll: function (e) {
                return Te(e, "nextSibling")
            }, prevAll: function (e) {
                return Te(e, "previousSibling")
            }, nextUntil: function (e, t, n) {
                return Te(e, "nextSibling", n)
            }, prevUntil: function (e, t, n) {
                return Te(e, "previousSibling", n)
            }, siblings: function (e) {
                return ke((e.parentNode || {}).firstChild, e)
            }, children: function (e) {
                return ke(e.firstChild)
            }, contents: function (e) {
                return ge.nodeName(e, "iframe") ? e.contentDocument || e.contentWindow.document : ge.merge([], e.childNodes)
            }
        }, function (e, t) {
            ge.fn[e] = function (n, r) {
                var i = ge.map(this, t, n);
                return "Until" !== e.slice(-5) && (r = n), r && "string" == typeof r && (i = ge.filter(r, i)), this.length > 1 && (je[e] || (i = ge.uniqueSort(i)), Le.test(e) && (i = i.reverse())), this.pushStack(i)
            }
        });
        var _e = /\S+/g;
        ge.Callbacks = function (e) {
            e = "string" == typeof e ? u(e) : ge.extend({}, e);
            var t, n, r, i, o = [], a = [], s = -1, l = function () {
                for (i = e.once, r = t = !0; a.length; s = -1)for (n = a.shift(); ++s < o.length;)o[s].apply(n[0], n[1]) === !1 && e.stopOnFalse && (s = o.length, n = !1);
                e.memory || (n = !1), t = !1, i && (o = n ? [] : "")
            }, c = {
                add: function () {
                    return o && (n && !t && (s = o.length - 1, a.push(n)), function t(n) {
                        ge.each(n, function (n, r) {
                            ge.isFunction(r) ? e.unique && c.has(r) || o.push(r) : r && r.length && "string" !== ge.type(r) && t(r)
                        })
                    }(arguments), n && !t && l()), this
                }, remove: function () {
                    return ge.each(arguments, function (e, t) {
                        for (var n; (n = ge.inArray(t, o, n)) > -1;)o.splice(n, 1), n <= s && s--
                    }), this
                }, has: function (e) {
                    return e ? ge.inArray(e, o) > -1 : o.length > 0
                }, empty: function () {
                    return o && (o = []), this
                }, disable: function () {
                    return i = a = [], o = n = "", this
                }, disabled: function () {
                    return !o
                }, lock: function () {
                    return i = !0, n || c.disable(), this
                }, locked: function () {
                    return !!i
                }, fireWith: function (e, n) {
                    return i || (n = n || [], n = [e, n.slice ? n.slice() : n], a.push(n), t || l()), this
                }, fire: function () {
                    return c.fireWith(this, arguments), this
                }, fired: function () {
                    return !!r
                }
            };
            return c
        }, ge.extend({
            Deferred: function (e) {
                var t = [["resolve", "done", ge.Callbacks("once memory"), "resolved"], ["reject", "fail", ge.Callbacks("once memory"), "rejected"], ["notify", "progress", ge.Callbacks("memory")]],
                    n = "pending", r = {
                        state: function () {
                            return n
                        }, always: function () {
                            return i.done(arguments).fail(arguments), this
                        }, then: function () {
                            var e = arguments;
                            return ge.Deferred(function (n) {
                                ge.each(t, function (t, o) {
                                    var a = ge.isFunction(e[t]) && e[t];
                                    i[o[1]](function () {
                                        var e = a && a.apply(this, arguments);
                                        e && ge.isFunction(e.promise) ? e.promise().progress(n.notify).done(n.resolve).fail(n.reject) : n[o[0] + "With"](this === r ? n.promise() : this, a ? [e] : arguments)
                                    })
                                }), e = null
                            }).promise()
                        }, promise: function (e) {
                            return null != e ? ge.extend(e, r) : r
                        }
                    }, i = {};
                return r.pipe = r.then, ge.each(t, function (e, o) {
                    var a = o[2], s = o[3];
                    r[o[1]] = a.add, s && a.add(function () {
                        n = s
                    }, t[1 ^ e][2].disable, t[2][2].lock), i[o[0]] = function () {
                        return i[o[0] + "With"](this === i ? r : this, arguments), this
                    }, i[o[0] + "With"] = a.fireWith
                }), r.promise(i), e && e.call(i, i), i
            }, when: function (e) {
                var t, n, r, i = 0, o = le.call(arguments), a = o.length,
                    s = 1 !== a || e && ge.isFunction(e.promise) ? a : 0, l = 1 === s ? e : ge.Deferred(),
                    u = function (e, n, r) {
                        return function (i) {
                            n[e] = this, r[e] = arguments.length > 1 ? le.call(arguments) : i, r === t ? l.notifyWith(n, r) : --s || l.resolveWith(n, r)
                        }
                    };
                if (a > 1)for (t = new Array(a), n = new Array(a), r = new Array(a); i < a; i++)o[i] && ge.isFunction(o[i].promise) ? o[i].promise().progress(u(i, n, t)).done(u(i, r, o)).fail(l.reject) : --s;
                return s || l.resolveWith(r, o), l.promise()
            }
        });
        var He;
        ge.fn.ready = function (e) {
            return ge.ready.promise().done(e), this
        }, ge.extend({
            isReady: !1, readyWait: 1, holdReady: function (e) {
                e ? ge.readyWait++ : ge.ready(!0)
            }, ready: function (e) {
                (e === !0 ? --ge.readyWait : ge.isReady) || (ge.isReady = !0, e !== !0 && --ge.readyWait > 0 || (He.resolveWith(se, [ge]), ge.fn.triggerHandler && (ge(se).triggerHandler("ready"), ge(se).off("ready"))))
            }
        }), ge.ready.promise = function (e) {
            if (!He)if (He = ge.Deferred(), "complete" === se.readyState || "loading" !== se.readyState && !se.documentElement.doScroll) n.setTimeout(ge.ready); else if (se.addEventListener) se.addEventListener("DOMContentLoaded", d), n.addEventListener("load", d); else {
                se.attachEvent("onreadystatechange", d), n.attachEvent("onload", d);
                var t = !1;
                try {
                    t = null == n.frameElement && se.documentElement
                } catch (e) {
                }
                t && t.doScroll && !function e() {
                    if (!ge.isReady) {
                        try {
                            t.doScroll("left")
                        } catch (t) {
                            return n.setTimeout(e, 50)
                        }
                        c(), ge.ready()
                    }
                }()
            }
            return He.promise(e)
        }, ge.ready.promise();
        var qe;
        for (qe in ge(me))break;
        me.ownFirst = "0" === qe, me.inlineBlockNeedsLayout = !1, ge(function () {
            var e, t, n, r;
            n = se.getElementsByTagName("body")[0], n && n.style && (t = se.createElement("div"), r = se.createElement("div"), r.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(r).appendChild(t), "undefined" != typeof t.style.zoom && (t.style.cssText = "display:inline;margin:0;border:0;padding:1px;width:1px;zoom:1", me.inlineBlockNeedsLayout = e = 3 === t.offsetWidth, e && (n.style.zoom = 1)), n.removeChild(r))
        }), function () {
            var e = se.createElement("div");
            me.deleteExpando = !0;
            try {
                delete e.test
            } catch (e) {
                me.deleteExpando = !1
            }
            e = null
        }();
        var Me = function (e) {
            var t = ge.noData[(e.nodeName + " ").toLowerCase()], n = +e.nodeType || 1;
            return (1 === n || 9 === n) && (!t || t !== !0 && e.getAttribute("classid") === t)
        }, Oe = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/, Ie = /([A-Z])/g;
        ge.extend({
            cache: {},
            noData: {"applet ": !0, "embed ": !0, "object ": "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"},
            hasData: function (e) {
                return e = e.nodeType ? ge.cache[e[ge.expando]] : e[ge.expando], !!e && !f(e)
            },
            data: function (e, t, n) {
                return h(e, t, n)
            },
            removeData: function (e, t) {
                return m(e, t)
            },
            _data: function (e, t, n) {
                return h(e, t, n, !0)
            },
            _removeData: function (e, t) {
                return m(e, t, !0)
            }
        }), ge.fn.extend({
            data: function (e, t) {
                var n, r, i, o = this[0], a = o && o.attributes;
                if (void 0 === e) {
                    if (this.length && (i = ge.data(o), 1 === o.nodeType && !ge._data(o, "parsedAttrs"))) {
                        for (n = a.length; n--;)a[n] && (r = a[n].name, 0 === r.indexOf("data-") && (r = ge.camelCase(r.slice(5)), p(o, r, i[r])));
                        ge._data(o, "parsedAttrs", !0)
                    }
                    return i
                }
                return "object" == typeof e ? this.each(function () {
                    ge.data(this, e)
                }) : arguments.length > 1 ? this.each(function () {
                    ge.data(this, e, t)
                }) : o ? p(o, e, ge.data(o, e)) : void 0
            }, removeData: function (e) {
                return this.each(function () {
                    ge.removeData(this, e)
                })
            }
        }), ge.extend({
            queue: function (e, t, n) {
                var r;
                if (e)return t = (t || "fx") + "queue", r = ge._data(e, t), n && (!r || ge.isArray(n) ? r = ge._data(e, t, ge.makeArray(n)) : r.push(n)), r || []
            }, dequeue: function (e, t) {
                t = t || "fx";
                var n = ge.queue(e, t), r = n.length, i = n.shift(), o = ge._queueHooks(e, t), a = function () {
                    ge.dequeue(e, t)
                };
                "inprogress" === i && (i = n.shift(), r--), i && ("fx" === t && n.unshift("inprogress"), delete o.stop, i.call(e, a, o)), !r && o && o.empty.fire()
            }, _queueHooks: function (e, t) {
                var n = t + "queueHooks";
                return ge._data(e, n) || ge._data(e, n, {
                        empty: ge.Callbacks("once memory").add(function () {
                            ge._removeData(e, t + "queue"), ge._removeData(e, n)
                        })
                    })
            }
        }), ge.fn.extend({
            queue: function (e, t) {
                var n = 2;
                return "string" != typeof e && (t = e, e = "fx", n--), arguments.length < n ? ge.queue(this[0], e) : void 0 === t ? this : this.each(function () {
                    var n = ge.queue(this, e, t);
                    ge._queueHooks(this, e), "fx" === e && "inprogress" !== n[0] && ge.dequeue(this, e)
                })
            }, dequeue: function (e) {
                return this.each(function () {
                    ge.dequeue(this, e)
                })
            }, clearQueue: function (e) {
                return this.queue(e || "fx", [])
            }, promise: function (e, t) {
                var n, r = 1, i = ge.Deferred(), o = this, a = this.length, s = function () {
                    --r || i.resolveWith(o, [o])
                };
                for ("string" != typeof e && (t = e, e = void 0), e = e || "fx"; a--;)n = ge._data(o[a], e + "queueHooks"), n && n.empty && (r++, n.empty.add(s));
                return s(), i.promise(t)
            }
        }), function () {
            var e;
            me.shrinkWrapBlocks = function () {
                if (null != e)return e;
                e = !1;
                var t, n, r;
                return n = se.getElementsByTagName("body")[0], n && n.style ? (t = se.createElement("div"), r = se.createElement("div"), r.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(r).appendChild(t), "undefined" != typeof t.style.zoom && (t.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:1px;width:1px;zoom:1", t.appendChild(se.createElement("div")).style.width = "5px", e = 3 !== t.offsetWidth), n.removeChild(r), e) : void 0
            }
        }();
        var Fe = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source,
            Be = new RegExp("^(?:([+-])=|)(" + Fe + ")([a-z%]*)$", "i"), Re = ["Top", "Right", "Bottom", "Left"],
            We = function (e, t) {
                return e = t || e, "none" === ge.css(e, "display") || !ge.contains(e.ownerDocument, e)
            }, ze = function (e, t, n, r, i, o, a) {
                var s = 0, l = e.length, u = null == n;
                if ("object" === ge.type(n)) {
                    i = !0;
                    for (s in n)ze(e, t, s, n[s], !0, o, a)
                } else if (void 0 !== r && (i = !0, ge.isFunction(r) || (a = !0), u && (a ? (t.call(e, r), t = null) : (u = t, t = function (e, t, n) {
                        return u.call(ge(e), n)
                    })), t))for (; s < l; s++)t(e[s], n, a ? r : r.call(e[s], s, t(e[s], n)));
                return i ? e : u ? t.call(e) : l ? t(e[0], n) : o
            }, $e = /^(?:checkbox|radio)$/i, Xe = /<([\w:-]+)/, Ve = /^$|\/(?:java|ecma)script/i, Ge = /^\s+/,
            Ue = "abbr|article|aside|audio|bdi|canvas|data|datalist|details|dialog|figcaption|figure|footer|header|hgroup|main|mark|meter|nav|output|picture|progress|section|summary|template|time|video";
        !function () {
            var e = se.createElement("div"), t = se.createDocumentFragment(), n = se.createElement("input");
            e.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", me.leadingWhitespace = 3 === e.firstChild.nodeType, me.tbody = !e.getElementsByTagName("tbody").length, me.htmlSerialize = !!e.getElementsByTagName("link").length, me.html5Clone = "<:nav></:nav>" !== se.createElement("nav").cloneNode(!0).outerHTML, n.type = "checkbox", n.checked = !0, t.appendChild(n), me.appendChecked = n.checked, e.innerHTML = "<textarea>x</textarea>", me.noCloneChecked = !!e.cloneNode(!0).lastChild.defaultValue, t.appendChild(e), n = se.createElement("input"), n.setAttribute("type", "radio"), n.setAttribute("checked", "checked"), n.setAttribute("name", "t"), e.appendChild(n), me.checkClone = e.cloneNode(!0).cloneNode(!0).lastChild.checked, me.noCloneEvent = !!e.addEventListener, e[ge.expando] = 1, me.attributes = !e.getAttribute(ge.expando)
        }();
        var Qe = {
            option: [1, "<select multiple='multiple'>", "</select>"],
            legend: [1, "<fieldset>", "</fieldset>"],
            area: [1, "<map>", "</map>"],
            param: [1, "<object>", "</object>"],
            thead: [1, "<table>", "</table>"],
            tr: [2, "<table><tbody>", "</tbody></table>"],
            col: [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"],
            td: [3, "<table><tbody><tr>", "</tr></tbody></table>"],
            _default: me.htmlSerialize ? [0, "", ""] : [1, "X<div>", "</div>"]
        };
        Qe.optgroup = Qe.option, Qe.tbody = Qe.tfoot = Qe.colgroup = Qe.caption = Qe.thead, Qe.th = Qe.td;
        var Je = /<|&#?\w+;/, Ye = /<tbody/i;
        !function () {
            var e, t, r = se.createElement("div");
            for (e in{
                submit: !0,
                change: !0,
                focusin: !0
            })t = "on" + e, (me[e] = t in n) || (r.setAttribute(t, "t"), me[e] = r.attributes[t].expando === !1);
            r = null
        }();
        var Ze = /^(?:input|select|textarea)$/i, Ke = /^key/, et = /^(?:mouse|pointer|contextmenu|drag|drop)|click/,
            tt = /^(?:focusinfocus|focusoutblur)$/, nt = /^([^.]*)(?:\.(.+)|)/;
        ge.event = {
            global: {},
            add: function (e, t, n, r, i) {
                var o, a, s, l, u, c, d, p, f, h, m, v = ge._data(e);
                if (v) {
                    for (n.handler && (l = n, n = l.handler, i = l.selector), n.guid || (n.guid = ge.guid++), (a = v.events) || (a = v.events = {}), (c = v.handle) || (c = v.handle = function (e) {
                        return "undefined" == typeof ge || e && ge.event.triggered === e.type ? void 0 : ge.event.dispatch.apply(c.elem, arguments)
                    }, c.elem = e), t = (t || "").match(_e) || [""], s = t.length; s--;)o = nt.exec(t[s]) || [], f = m = o[1], h = (o[2] || "").split(".").sort(), f && (u = ge.event.special[f] || {}, f = (i ? u.delegateType : u.bindType) || f, u = ge.event.special[f] || {}, d = ge.extend({
                        type: f,
                        origType: m,
                        data: r,
                        handler: n,
                        guid: n.guid,
                        selector: i,
                        needsContext: i && ge.expr.match.needsContext.test(i),
                        namespace: h.join(".")
                    }, l), (p = a[f]) || (p = a[f] = [], p.delegateCount = 0, u.setup && u.setup.call(e, r, h, c) !== !1 || (e.addEventListener ? e.addEventListener(f, c, !1) : e.attachEvent && e.attachEvent("on" + f, c))), u.add && (u.add.call(e, d), d.handler.guid || (d.handler.guid = n.guid)), i ? p.splice(p.delegateCount++, 0, d) : p.push(d), ge.event.global[f] = !0);
                    e = null
                }
            },
            remove: function (e, t, n, r, i) {
                var o, a, s, l, u, c, d, p, f, h, m, v = ge.hasData(e) && ge._data(e);
                if (v && (c = v.events)) {
                    for (t = (t || "").match(_e) || [""], u = t.length; u--;)if (s = nt.exec(t[u]) || [], f = m = s[1], h = (s[2] || "").split(".").sort(), f) {
                        for (d = ge.event.special[f] || {}, f = (r ? d.delegateType : d.bindType) || f, p = c[f] || [], s = s[2] && new RegExp("(^|\\.)" + h.join("\\.(?:.*\\.|)") + "(\\.|$)"), l = o = p.length; o--;)a = p[o], !i && m !== a.origType || n && n.guid !== a.guid || s && !s.test(a.namespace) || r && r !== a.selector && ("**" !== r || !a.selector) || (p.splice(o, 1), a.selector && p.delegateCount--, d.remove && d.remove.call(e, a));
                        l && !p.length && (d.teardown && d.teardown.call(e, h, v.handle) !== !1 || ge.removeEvent(e, f, v.handle), delete c[f])
                    } else for (f in c)ge.event.remove(e, f + t[u], n, r, !0);
                    ge.isEmptyObject(c) && (delete v.handle, ge._removeData(e, "events"))
                }
            },
            trigger: function (e, t, r, i) {
                var o, a, s, l, u, c, d, p = [r || se], f = he.call(e, "type") ? e.type : e,
                    h = he.call(e, "namespace") ? e.namespace.split(".") : [];
                if (s = c = r = r || se, 3 !== r.nodeType && 8 !== r.nodeType && !tt.test(f + ge.event.triggered) && (f.indexOf(".") > -1 && (h = f.split("."), f = h.shift(), h.sort()), a = f.indexOf(":") < 0 && "on" + f, e = e[ge.expando] ? e : new ge.Event(f, "object" == typeof e && e), e.isTrigger = i ? 2 : 3, e.namespace = h.join("."), e.rnamespace = e.namespace ? new RegExp("(^|\\.)" + h.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, e.result = void 0, e.target || (e.target = r), t = null == t ? [e] : ge.makeArray(t, [e]), u = ge.event.special[f] || {}, i || !u.trigger || u.trigger.apply(r, t) !== !1)) {
                    if (!i && !u.noBubble && !ge.isWindow(r)) {
                        for (l = u.delegateType || f, tt.test(l + f) || (s = s.parentNode); s; s = s.parentNode)p.push(s), c = s;
                        c === (r.ownerDocument || se) && p.push(c.defaultView || c.parentWindow || n)
                    }
                    for (d = 0; (s = p[d++]) && !e.isPropagationStopped();)e.type = d > 1 ? l : u.bindType || f, o = (ge._data(s, "events") || {})[e.type] && ge._data(s, "handle"), o && o.apply(s, t), o = a && s[a], o && o.apply && Me(s) && (e.result = o.apply(s, t), e.result === !1 && e.preventDefault());
                    if (e.type = f, !i && !e.isDefaultPrevented() && (!u._default || u._default.apply(p.pop(), t) === !1) && Me(r) && a && r[f] && !ge.isWindow(r)) {
                        c = r[a], c && (r[a] = null), ge.event.triggered = f;
                        try {
                            r[f]()
                        } catch (e) {
                        }
                        ge.event.triggered = void 0, c && (r[a] = c)
                    }
                    return e.result
                }
            },
            dispatch: function (e) {
                e = ge.event.fix(e);
                var t, n, r, i, o, a = [], s = le.call(arguments), l = (ge._data(this, "events") || {})[e.type] || [],
                    u = ge.event.special[e.type] || {};
                if (s[0] = e, e.delegateTarget = this, !u.preDispatch || u.preDispatch.call(this, e) !== !1) {
                    for (a = ge.event.handlers.call(this, e, l), t = 0; (i = a[t++]) && !e.isPropagationStopped();)for (e.currentTarget = i.elem, n = 0; (o = i.handlers[n++]) && !e.isImmediatePropagationStopped();)e.rnamespace && !e.rnamespace.test(o.namespace) || (e.handleObj = o, e.data = o.data, r = ((ge.event.special[o.origType] || {}).handle || o.handler).apply(i.elem, s), void 0 !== r && (e.result = r) === !1 && (e.preventDefault(), e.stopPropagation()));
                    return u.postDispatch && u.postDispatch.call(this, e), e.result
                }
            },
            handlers: function (e, t) {
                var n, r, i, o, a = [], s = t.delegateCount, l = e.target;
                if (s && l.nodeType && ("click" !== e.type || isNaN(e.button) || e.button < 1))for (; l != this; l = l.parentNode || this)if (1 === l.nodeType && (l.disabled !== !0 || "click" !== e.type)) {
                    for (r = [], n = 0; n < s; n++)o = t[n], i = o.selector + " ", void 0 === r[i] && (r[i] = o.needsContext ? ge(i, this).index(l) > -1 : ge.find(i, this, null, [l]).length), r[i] && r.push(o);
                    r.length && a.push({elem: l, handlers: r})
                }
                return s < t.length && a.push({elem: this, handlers: t.slice(s)}), a
            },
            fix: function (e) {
                if (e[ge.expando])return e;
                var t, n, r, i = e.type, o = e, a = this.fixHooks[i];
                for (a || (this.fixHooks[i] = a = et.test(i) ? this.mouseHooks : Ke.test(i) ? this.keyHooks : {}), r = a.props ? this.props.concat(a.props) : this.props, e = new ge.Event(o), t = r.length; t--;)n = r[t], e[n] = o[n];
                return e.target || (e.target = o.srcElement || se), 3 === e.target.nodeType && (e.target = e.target.parentNode), e.metaKey = !!e.metaKey, a.filter ? a.filter(e, o) : e
            },
            props: "altKey bubbles cancelable ctrlKey currentTarget detail eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "),
            fixHooks: {},
            keyHooks: {
                props: "char charCode key keyCode".split(" "), filter: function (e, t) {
                    return null == e.which && (e.which = null != t.charCode ? t.charCode : t.keyCode), e
                }
            },
            mouseHooks: {
                props: "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "),
                filter: function (e, t) {
                    var n, r, i, o = t.button, a = t.fromElement;
                    return null == e.pageX && null != t.clientX && (r = e.target.ownerDocument || se, i = r.documentElement, n = r.body, e.pageX = t.clientX + (i && i.scrollLeft || n && n.scrollLeft || 0) - (i && i.clientLeft || n && n.clientLeft || 0), e.pageY = t.clientY + (i && i.scrollTop || n && n.scrollTop || 0) - (i && i.clientTop || n && n.clientTop || 0)), !e.relatedTarget && a && (e.relatedTarget = a === e.target ? t.toElement : a), e.which || void 0 === o || (e.which = 1 & o ? 1 : 2 & o ? 3 : 4 & o ? 2 : 0), e
                }
            },
            special: {
                load: {noBubble: !0}, focus: {
                    trigger: function () {
                        if (this !== k() && this.focus)try {
                            return this.focus(), !1
                        } catch (e) {
                        }
                    }, delegateType: "focusin"
                }, blur: {
                    trigger: function () {
                        if (this === k() && this.blur)return this.blur(), !1
                    }, delegateType: "focusout"
                }, click: {
                    trigger: function () {
                        if (ge.nodeName(this, "input") && "checkbox" === this.type && this.click)return this.click(), !1
                    }, _default: function (e) {
                        return ge.nodeName(e.target, "a")
                    }
                }, beforeunload: {
                    postDispatch: function (e) {
                        void 0 !== e.result && e.originalEvent && (e.originalEvent.returnValue = e.result)
                    }
                }
            },
            simulate: function (e, t, n) {
                var r = ge.extend(new ge.Event, n, {type: e, isSimulated: !0});
                ge.event.trigger(r, null, t), r.isDefaultPrevented() && n.preventDefault()
            }
        }, ge.removeEvent = se.removeEventListener ? function (e, t, n) {
            e.removeEventListener && e.removeEventListener(t, n)
        } : function (e, t, n) {
            var r = "on" + t;
            e.detachEvent && ("undefined" == typeof e[r] && (e[r] = null), e.detachEvent(r, n))
        }, ge.Event = function (e, t) {
            return this instanceof ge.Event ? (e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || void 0 === e.defaultPrevented && e.returnValue === !1 ? C : T) : this.type = e, t && ge.extend(this, t), this.timeStamp = e && e.timeStamp || ge.now(), void(this[ge.expando] = !0)) : new ge.Event(e, t)
        }, ge.Event.prototype = {
            constructor: ge.Event,
            isDefaultPrevented: T,
            isPropagationStopped: T,
            isImmediatePropagationStopped: T,
            preventDefault: function () {
                var e = this.originalEvent;
                this.isDefaultPrevented = C, e && (e.preventDefault ? e.preventDefault() : e.returnValue = !1)
            },
            stopPropagation: function () {
                var e = this.originalEvent;
                this.isPropagationStopped = C, e && !this.isSimulated && (e.stopPropagation && e.stopPropagation(), e.cancelBubble = !0)
            },
            stopImmediatePropagation: function () {
                var e = this.originalEvent;
                this.isImmediatePropagationStopped = C, e && e.stopImmediatePropagation && e.stopImmediatePropagation(), this.stopPropagation()
            }
        }, ge.each({
            mouseenter: "mouseover",
            mouseleave: "mouseout",
            pointerenter: "pointerover",
            pointerleave: "pointerout"
        }, function (e, t) {
            ge.event.special[e] = {
                delegateType: t, bindType: t, handle: function (e) {
                    var n, r = this, i = e.relatedTarget, o = e.handleObj;
                    return i && (i === r || ge.contains(r, i)) || (e.type = o.origType, n = o.handler.apply(this, arguments), e.type = t), n
                }
            }
        }), me.submit || (ge.event.special.submit = {
            setup: function () {
                return !ge.nodeName(this, "form") && void ge.event.add(this, "click._submit keypress._submit", function (e) {
                        var t = e.target,
                            n = ge.nodeName(t, "input") || ge.nodeName(t, "button") ? ge.prop(t, "form") : void 0;
                        n && !ge._data(n, "submit") && (ge.event.add(n, "submit._submit", function (e) {
                            e._submitBubble = !0
                        }), ge._data(n, "submit", !0))
                    })
            }, postDispatch: function (e) {
                e._submitBubble && (delete e._submitBubble, this.parentNode && !e.isTrigger && ge.event.simulate("submit", this.parentNode, e))
            }, teardown: function () {
                return !ge.nodeName(this, "form") && void ge.event.remove(this, "._submit")
            }
        }), me.change || (ge.event.special.change = {
            setup: function () {
                return Ze.test(this.nodeName) ? ("checkbox" !== this.type && "radio" !== this.type || (ge.event.add(this, "propertychange._change", function (e) {
                    "checked" === e.originalEvent.propertyName && (this._justChanged = !0)
                }), ge.event.add(this, "click._change", function (e) {
                    this._justChanged && !e.isTrigger && (this._justChanged = !1), ge.event.simulate("change", this, e)
                })), !1) : void ge.event.add(this, "beforeactivate._change", function (e) {
                    var t = e.target;
                    Ze.test(t.nodeName) && !ge._data(t, "change") && (ge.event.add(t, "change._change", function (e) {
                        !this.parentNode || e.isSimulated || e.isTrigger || ge.event.simulate("change", this.parentNode, e)
                    }), ge._data(t, "change", !0))
                })
            }, handle: function (e) {
                var t = e.target;
                if (this !== t || e.isSimulated || e.isTrigger || "radio" !== t.type && "checkbox" !== t.type)return e.handleObj.handler.apply(this, arguments)
            }, teardown: function () {
                return ge.event.remove(this, "._change"), !Ze.test(this.nodeName)
            }
        }), me.focusin || ge.each({focus: "focusin", blur: "focusout"}, function (e, t) {
            var n = function (e) {
                ge.event.simulate(t, e.target, ge.event.fix(e))
            };
            ge.event.special[t] = {
                setup: function () {
                    var r = this.ownerDocument || this, i = ge._data(r, t);
                    i || r.addEventListener(e, n, !0), ge._data(r, t, (i || 0) + 1)
                }, teardown: function () {
                    var r = this.ownerDocument || this, i = ge._data(r, t) - 1;
                    i ? ge._data(r, t, i) : (r.removeEventListener(e, n, !0), ge._removeData(r, t))
                }
            }
        }), ge.fn.extend({
            on: function (e, t, n, r) {
                return E(this, e, t, n, r)
            }, one: function (e, t, n, r) {
                return E(this, e, t, n, r, 1)
            }, off: function (e, t, n) {
                var r, i;
                if (e && e.preventDefault && e.handleObj)return r = e.handleObj, ge(e.delegateTarget).off(r.namespace ? r.origType + "." + r.namespace : r.origType, r.selector, r.handler), this;
                if ("object" == typeof e) {
                    for (i in e)this.off(i, t, e[i]);
                    return this
                }
                return t !== !1 && "function" != typeof t || (n = t, t = void 0), n === !1 && (n = T), this.each(function () {
                    ge.event.remove(this, e, n, t)
                })
            }, trigger: function (e, t) {
                return this.each(function () {
                    ge.event.trigger(e, t, this)
                })
            }, triggerHandler: function (e, t) {
                var n = this[0];
                if (n)return ge.event.trigger(e, t, n, !0)
            }
        });
        var rt = / jQuery\d+="(?:null|\d+)"/g, it = new RegExp("<(?:" + Ue + ")[\\s/>]", "i"),
            ot = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:-]+)[^>]*)\/>/gi,
            at = /<script|<style|<link/i, st = /checked\s*(?:[^=]|=\s*.checked.)/i, lt = /^true\/(.*)/,
            ut = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g, ct = g(se), dt = ct.appendChild(se.createElement("div"));
        ge.extend({
            htmlPrefilter: function (e) {
                return e.replace(ot, "<$1></$2>")
            }, clone: function (e, t, n) {
                var r, i, o, a, s, l = ge.contains(e.ownerDocument, e);
                if (me.html5Clone || ge.isXMLDoc(e) || !it.test("<" + e.nodeName + ">") ? o = e.cloneNode(!0) : (dt.innerHTML = e.outerHTML, dt.removeChild(o = dt.firstChild)), !(me.noCloneEvent && me.noCloneChecked || 1 !== e.nodeType && 11 !== e.nodeType || ge.isXMLDoc(e)))for (r = y(o), s = y(e), a = 0; null != (i = s[a]); ++a)r[a] && D(i, r[a]);
                if (t)if (n)for (s = s || y(e), r = r || y(o), a = 0; null != (i = s[a]); a++)P(i, r[a]); else P(e, o);
                return r = y(o, "script"), r.length > 0 && b(r, !l && y(e, "script")), r = s = i = null, o
            }, cleanData: function (e, t) {
                for (var n, r, i, o, a = 0, s = ge.expando, l = ge.cache, u = me.attributes, c = ge.event.special; null != (n = e[a]); a++)if ((t || Me(n)) && (i = n[s], o = i && l[i])) {
                    if (o.events)for (r in o.events)c[r] ? ge.event.remove(n, r) : ge.removeEvent(n, r, o.handle);
                    l[i] && (delete l[i], u || "undefined" == typeof n.removeAttribute ? n[s] = void 0 : n.removeAttribute(s), ae.push(i))
                }
            }
        }), ge.fn.extend({
            domManip: L, detach: function (e) {
                return j(this, e, !0)
            }, remove: function (e) {
                return j(this, e)
            }, text: function (e) {
                return ze(this, function (e) {
                    return void 0 === e ? ge.text(this) : this.empty().append((this[0] && this[0].ownerDocument || se).createTextNode(e))
                }, null, e, arguments.length)
            }, append: function () {
                return L(this, arguments, function (e) {
                    if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                        var t = N(this, e);
                        t.appendChild(e)
                    }
                })
            }, prepend: function () {
                return L(this, arguments, function (e) {
                    if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                        var t = N(this, e);
                        t.insertBefore(e, t.firstChild)
                    }
                })
            }, before: function () {
                return L(this, arguments, function (e) {
                    this.parentNode && this.parentNode.insertBefore(e, this)
                })
            }, after: function () {
                return L(this, arguments, function (e) {
                    this.parentNode && this.parentNode.insertBefore(e, this.nextSibling)
                })
            }, empty: function () {
                for (var e, t = 0; null != (e = this[t]); t++) {
                    for (1 === e.nodeType && ge.cleanData(y(e, !1)); e.firstChild;)e.removeChild(e.firstChild);
                    e.options && ge.nodeName(e, "select") && (e.options.length = 0)
                }
                return this
            }, clone: function (e, t) {
                return e = null != e && e, t = null == t ? e : t, this.map(function () {
                    return ge.clone(this, e, t)
                })
            }, html: function (e) {
                return ze(this, function (e) {
                    var t = this[0] || {}, n = 0, r = this.length;
                    if (void 0 === e)return 1 === t.nodeType ? t.innerHTML.replace(rt, "") : void 0;
                    if ("string" == typeof e && !at.test(e) && (me.htmlSerialize || !it.test(e)) && (me.leadingWhitespace || !Ge.test(e)) && !Qe[(Xe.exec(e) || ["", ""])[1].toLowerCase()]) {
                        e = ge.htmlPrefilter(e);
                        try {
                            for (; n < r; n++)t = this[n] || {}, 1 === t.nodeType && (ge.cleanData(y(t, !1)), t.innerHTML = e);
                            t = 0
                        } catch (e) {
                        }
                    }
                    t && this.empty().append(e)
                }, null, e, arguments.length)
            }, replaceWith: function () {
                var e = [];
                return L(this, arguments, function (t) {
                    var n = this.parentNode;
                    ge.inArray(this, e) < 0 && (ge.cleanData(y(this)), n && n.replaceChild(t, this))
                }, e)
            }
        }), ge.each({
            appendTo: "append",
            prependTo: "prepend",
            insertBefore: "before",
            insertAfter: "after",
            replaceAll: "replaceWith"
        }, function (e, t) {
            ge.fn[e] = function (e) {
                for (var n, r = 0, i = [], o = ge(e), a = o.length - 1; r <= a; r++)n = r === a ? this : this.clone(!0), ge(o[r])[t](n), ce.apply(i, n.get());
                return this.pushStack(i)
            }
        });
        var pt, ft = {HTML: "block", BODY: "block"}, ht = /^margin/,
            mt = new RegExp("^(" + Fe + ")(?!px)[a-z%]+$", "i"), vt = function (e, t, n, r) {
                var i, o, a = {};
                for (o in t)a[o] = e.style[o], e.style[o] = t[o];
                i = n.apply(e, r || []);
                for (o in t)e.style[o] = a[o];
                return i
            }, gt = se.documentElement;
        !function () {
            function e() {
                var e, c, d = se.documentElement;
                d.appendChild(l), u.style.cssText = "-webkit-box-sizing:border-box;box-sizing:border-box;position:relative;display:block;margin:auto;border:1px;padding:1px;top:1%;width:50%", t = i = s = !1, r = a = !0, n.getComputedStyle && (c = n.getComputedStyle(u), t = "1%" !== (c || {}).top, s = "2px" === (c || {}).marginLeft, i = "4px" === (c || {width: "4px"}).width, u.style.marginRight = "50%", r = "4px" === (c || {marginRight: "4px"}).marginRight, e = u.appendChild(se.createElement("div")), e.style.cssText = u.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:0", e.style.marginRight = e.style.width = "0", u.style.width = "1px", a = !parseFloat((n.getComputedStyle(e) || {}).marginRight), u.removeChild(e)), u.style.display = "none", o = 0 === u.getClientRects().length, o && (u.style.display = "", u.innerHTML = "<table><tr><td></td><td>t</td></tr></table>", u.childNodes[0].style.borderCollapse = "separate", e = u.getElementsByTagName("td"), e[0].style.cssText = "margin:0;border:0;padding:0;display:none", o = 0 === e[0].offsetHeight, o && (e[0].style.display = "", e[1].style.display = "none", o = 0 === e[0].offsetHeight)), d.removeChild(l)
            }

            var t, r, i, o, a, s, l = se.createElement("div"), u = se.createElement("div");
            u.style && (u.style.cssText = "float:left;opacity:.5", me.opacity = "0.5" === u.style.opacity, me.cssFloat = !!u.style.cssFloat, u.style.backgroundClip = "content-box", u.cloneNode(!0).style.backgroundClip = "", me.clearCloneStyle = "content-box" === u.style.backgroundClip, l = se.createElement("div"), l.style.cssText = "border:0;width:8px;height:0;top:0;left:-9999px;padding:0;margin-top:1px;position:absolute", u.innerHTML = "", l.appendChild(u), me.boxSizing = "" === u.style.boxSizing || "" === u.style.MozBoxSizing || "" === u.style.WebkitBoxSizing, ge.extend(me, {
                reliableHiddenOffsets: function () {
                    return null == t && e(), o
                }, boxSizingReliable: function () {
                    return null == t && e(), i
                }, pixelMarginRight: function () {
                    return null == t && e(), r
                }, pixelPosition: function () {
                    return null == t && e(), t
                }, reliableMarginRight: function () {
                    return null == t && e(), a
                }, reliableMarginLeft: function () {
                    return null == t && e(), s
                }
            }))
        }();
        var yt, bt, xt = /^(top|right|bottom|left)$/;
        n.getComputedStyle ? (yt = function (e) {
            var t = e.ownerDocument.defaultView;
            return t && t.opener || (t = n), t.getComputedStyle(e)
        }, bt = function (e, t, n) {
            var r, i, o, a, s = e.style;
            return n = n || yt(e), a = n ? n.getPropertyValue(t) || n[t] : void 0, "" !== a && void 0 !== a || ge.contains(e.ownerDocument, e) || (a = ge.style(e, t)), n && !me.pixelMarginRight() && mt.test(a) && ht.test(t) && (r = s.width, i = s.minWidth, o = s.maxWidth, s.minWidth = s.maxWidth = s.width = a, a = n.width, s.width = r, s.minWidth = i, s.maxWidth = o), void 0 === a ? a : a + ""
        }) : gt.currentStyle && (yt = function (e) {
                return e.currentStyle
            }, bt = function (e, t, n) {
                var r, i, o, a, s = e.style;
                return n = n || yt(e), a = n ? n[t] : void 0, null == a && s && s[t] && (a = s[t]), mt.test(a) && !xt.test(t) && (r = s.left, i = e.runtimeStyle, o = i && i.left, o && (i.left = e.currentStyle.left), s.left = "fontSize" === t ? "1em" : a, a = s.pixelLeft + "px", s.left = r, o && (i.left = o)), void 0 === a ? a : a + "" || "auto"
            });
        var wt = /alpha\([^)]*\)/i, Ct = /opacity\s*=\s*([^)]*)/i, Tt = /^(none|table(?!-c[ea]).+)/,
            kt = new RegExp("^(" + Fe + ")(.*)$", "i"),
            Et = {position: "absolute", visibility: "hidden", display: "block"},
            Nt = {letterSpacing: "0", fontWeight: "400"}, St = ["Webkit", "O", "Moz", "ms"],
            At = se.createElement("div").style;
        ge.extend({
            cssHooks: {
                opacity: {
                    get: function (e, t) {
                        if (t) {
                            var n = bt(e, "opacity");
                            return "" === n ? "1" : n
                        }
                    }
                }
            },
            cssNumber: {
                animationIterationCount: !0,
                columnCount: !0,
                fillOpacity: !0,
                flexGrow: !0,
                flexShrink: !0,
                fontWeight: !0,
                lineHeight: !0,
                opacity: !0,
                order: !0,
                orphans: !0,
                widows: !0,
                zIndex: !0,
                zoom: !0
            },
            cssProps: {float: me.cssFloat ? "cssFloat" : "styleFloat"},
            style: function (e, t, n, r) {
                if (e && 3 !== e.nodeType && 8 !== e.nodeType && e.style) {
                    var i, o, a, s = ge.camelCase(t), l = e.style;
                    if (t = ge.cssProps[s] || (ge.cssProps[s] = M(s) || s), a = ge.cssHooks[t] || ge.cssHooks[s], void 0 === n)return a && "get" in a && void 0 !== (i = a.get(e, !1, r)) ? i : l[t];
                    if (o = typeof n, "string" === o && (i = Be.exec(n)) && i[1] && (n = v(e, t, i), o = "number"), null != n && n === n && ("number" === o && (n += i && i[3] || (ge.cssNumber[s] ? "" : "px")), me.clearCloneStyle || "" !== n || 0 !== t.indexOf("background") || (l[t] = "inherit"), !(a && "set" in a && void 0 === (n = a.set(e, n, r)))))try {
                        l[t] = n
                    } catch (e) {
                    }
                }
            },
            css: function (e, t, n, r) {
                var i, o, a, s = ge.camelCase(t);
                return t = ge.cssProps[s] || (ge.cssProps[s] = M(s) || s), a = ge.cssHooks[t] || ge.cssHooks[s], a && "get" in a && (o = a.get(e, !0, n)), void 0 === o && (o = bt(e, t, r)), "normal" === o && t in Nt && (o = Nt[t]), "" === n || n ? (i = parseFloat(o), n === !0 || isFinite(i) ? i || 0 : o) : o
            }
        }), ge.each(["height", "width"], function (e, t) {
            ge.cssHooks[t] = {
                get: function (e, n, r) {
                    if (n)return Tt.test(ge.css(e, "display")) && 0 === e.offsetWidth ? vt(e, Et, function () {
                        return B(e, t, r)
                    }) : B(e, t, r)
                }, set: function (e, n, r) {
                    var i = r && yt(e);
                    return I(e, n, r ? F(e, t, r, me.boxSizing && "border-box" === ge.css(e, "boxSizing", !1, i), i) : 0)
                }
            }
        }), me.opacity || (ge.cssHooks.opacity = {
            get: function (e, t) {
                return Ct.test((t && e.currentStyle ? e.currentStyle.filter : e.style.filter) || "") ? .01 * parseFloat(RegExp.$1) + "" : t ? "1" : ""
            }, set: function (e, t) {
                var n = e.style, r = e.currentStyle, i = ge.isNumeric(t) ? "alpha(opacity=" + 100 * t + ")" : "",
                    o = r && r.filter || n.filter || "";
                n.zoom = 1, (t >= 1 || "" === t) && "" === ge.trim(o.replace(wt, "")) && n.removeAttribute && (n.removeAttribute("filter"), "" === t || r && !r.filter) || (n.filter = wt.test(o) ? o.replace(wt, i) : o + " " + i)
            }
        }), ge.cssHooks.marginRight = q(me.reliableMarginRight, function (e, t) {
            if (t)return vt(e, {display: "inline-block"}, bt, [e, "marginRight"])
        }), ge.cssHooks.marginLeft = q(me.reliableMarginLeft, function (e, t) {
            if (t)return (parseFloat(bt(e, "marginLeft")) || (ge.contains(e.ownerDocument, e) ? e.getBoundingClientRect().left - vt(e, {marginLeft: 0}, function () {
                        return e.getBoundingClientRect().left
                    }) : 0)) + "px"
        }), ge.each({margin: "", padding: "", border: "Width"}, function (e, t) {
            ge.cssHooks[e + t] = {
                expand: function (n) {
                    for (var r = 0, i = {}, o = "string" == typeof n ? n.split(" ") : [n]; r < 4; r++)i[e + Re[r] + t] = o[r] || o[r - 2] || o[0];
                    return i
                }
            }, ht.test(e) || (ge.cssHooks[e + t].set = I)
        }), ge.fn.extend({
            css: function (e, t) {
                return ze(this, function (e, t, n) {
                    var r, i, o = {}, a = 0;
                    if (ge.isArray(t)) {
                        for (r = yt(e), i = t.length; a < i; a++)o[t[a]] = ge.css(e, t[a], !1, r);
                        return o
                    }
                    return void 0 !== n ? ge.style(e, t, n) : ge.css(e, t)
                }, e, t, arguments.length > 1)
            }, show: function () {
                return O(this, !0)
            }, hide: function () {
                return O(this)
            }, toggle: function (e) {
                return "boolean" == typeof e ? e ? this.show() : this.hide() : this.each(function () {
                    We(this) ? ge(this).show() : ge(this).hide()
                })
            }
        }), ge.Tween = R, R.prototype = {
            constructor: R, init: function (e, t, n, r, i, o) {
                this.elem = e, this.prop = n, this.easing = i || ge.easing._default, this.options = t, this.start = this.now = this.cur(), this.end = r, this.unit = o || (ge.cssNumber[n] ? "" : "px")
            }, cur: function () {
                var e = R.propHooks[this.prop];
                return e && e.get ? e.get(this) : R.propHooks._default.get(this)
            }, run: function (e) {
                var t, n = R.propHooks[this.prop];
                return this.options.duration ? this.pos = t = ge.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : this.pos = t = e, this.now = (this.end - this.start) * t + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), n && n.set ? n.set(this) : R.propHooks._default.set(this), this
            }
        }, R.prototype.init.prototype = R.prototype, R.propHooks = {
            _default: {
                get: function (e) {
                    var t;
                    return 1 !== e.elem.nodeType || null != e.elem[e.prop] && null == e.elem.style[e.prop] ? e.elem[e.prop] : (t = ge.css(e.elem, e.prop, ""), t && "auto" !== t ? t : 0)
                }, set: function (e) {
                    ge.fx.step[e.prop] ? ge.fx.step[e.prop](e) : 1 !== e.elem.nodeType || null == e.elem.style[ge.cssProps[e.prop]] && !ge.cssHooks[e.prop] ? e.elem[e.prop] = e.now : ge.style(e.elem, e.prop, e.now + e.unit)
                }
            }
        }, R.propHooks.scrollTop = R.propHooks.scrollLeft = {
            set: function (e) {
                e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
            }
        }, ge.easing = {
            linear: function (e) {
                return e
            }, swing: function (e) {
                return .5 - Math.cos(e * Math.PI) / 2
            }, _default: "swing"
        }, ge.fx = R.prototype.init, ge.fx.step = {};
        var Pt, Dt, Lt = /^(?:toggle|show|hide)$/, jt = /queueHooks$/;
        ge.Animation = ge.extend(G, {
            tweeners: {
                "*": [function (e, t) {
                    var n = this.createTween(e, t);
                    return v(n.elem, e, Be.exec(t), n), n
                }]
            }, tweener: function (e, t) {
                ge.isFunction(e) ? (t = e, e = ["*"]) : e = e.match(_e);
                for (var n, r = 0, i = e.length; r < i; r++)n = e[r], G.tweeners[n] = G.tweeners[n] || [], G.tweeners[n].unshift(t)
            }, prefilters: [X], prefilter: function (e, t) {
                t ? G.prefilters.unshift(e) : G.prefilters.push(e)
            }
        }), ge.speed = function (e, t, n) {
            var r = e && "object" == typeof e ? ge.extend({}, e) : {
                complete: n || !n && t || ge.isFunction(e) && e,
                duration: e,
                easing: n && t || t && !ge.isFunction(t) && t
            };
            return r.duration = ge.fx.off ? 0 : "number" == typeof r.duration ? r.duration : r.duration in ge.fx.speeds ? ge.fx.speeds[r.duration] : ge.fx.speeds._default, null != r.queue && r.queue !== !0 || (r.queue = "fx"), r.old = r.complete, r.complete = function () {
                ge.isFunction(r.old) && r.old.call(this), r.queue && ge.dequeue(this, r.queue)
            }, r
        }, ge.fn.extend({
            fadeTo: function (e, t, n, r) {
                return this.filter(We).css("opacity", 0).show().end().animate({opacity: t}, e, n, r)
            }, animate: function (e, t, n, r) {
                var i = ge.isEmptyObject(e), o = ge.speed(t, n, r), a = function () {
                    var t = G(this, ge.extend({}, e), o);
                    (i || ge._data(this, "finish")) && t.stop(!0)
                };
                return a.finish = a, i || o.queue === !1 ? this.each(a) : this.queue(o.queue, a)
            }, stop: function (e, t, n) {
                var r = function (e) {
                    var t = e.stop;
                    delete e.stop, t(n)
                };
                return "string" != typeof e && (n = t, t = e, e = void 0), t && e !== !1 && this.queue(e || "fx", []), this.each(function () {
                    var t = !0, i = null != e && e + "queueHooks", o = ge.timers, a = ge._data(this);
                    if (i) a[i] && a[i].stop && r(a[i]); else for (i in a)a[i] && a[i].stop && jt.test(i) && r(a[i]);
                    for (i = o.length; i--;)o[i].elem !== this || null != e && o[i].queue !== e || (o[i].anim.stop(n), t = !1, o.splice(i, 1));
                    !t && n || ge.dequeue(this, e)
                })
            }, finish: function (e) {
                return e !== !1 && (e = e || "fx"), this.each(function () {
                    var t, n = ge._data(this), r = n[e + "queue"], i = n[e + "queueHooks"], o = ge.timers,
                        a = r ? r.length : 0;
                    for (n.finish = !0, ge.queue(this, e, []), i && i.stop && i.stop.call(this, !0), t = o.length; t--;)o[t].elem === this && o[t].queue === e && (o[t].anim.stop(!0), o.splice(t, 1));
                    for (t = 0; t < a; t++)r[t] && r[t].finish && r[t].finish.call(this);
                    delete n.finish
                })
            }
        }), ge.each(["toggle", "show", "hide"], function (e, t) {
            var n = ge.fn[t];
            ge.fn[t] = function (e, r, i) {
                return null == e || "boolean" == typeof e ? n.apply(this, arguments) : this.animate(z(t, !0), e, r, i)
            }
        }), ge.each({
            slideDown: z("show"),
            slideUp: z("hide"),
            slideToggle: z("toggle"),
            fadeIn: {opacity: "show"},
            fadeOut: {opacity: "hide"},
            fadeToggle: {opacity: "toggle"}
        }, function (e, t) {
            ge.fn[e] = function (e, n, r) {
                return this.animate(t, e, n, r)
            }
        }), ge.timers = [], ge.fx.tick = function () {
            var e, t = ge.timers, n = 0;
            for (Pt = ge.now(); n < t.length; n++)e = t[n], e() || t[n] !== e || t.splice(n--, 1);
            t.length || ge.fx.stop(), Pt = void 0
        }, ge.fx.timer = function (e) {
            ge.timers.push(e), e() ? ge.fx.start() : ge.timers.pop()
        }, ge.fx.interval = 13, ge.fx.start = function () {
            Dt || (Dt = n.setInterval(ge.fx.tick, ge.fx.interval))
        }, ge.fx.stop = function () {
            n.clearInterval(Dt), Dt = null
        }, ge.fx.speeds = {slow: 600, fast: 200, _default: 400}, ge.fn.delay = function (e, t) {
            return e = ge.fx ? ge.fx.speeds[e] || e : e, t = t || "fx", this.queue(t, function (t, r) {
                var i = n.setTimeout(t, e);
                r.stop = function () {
                    n.clearTimeout(i)
                }
            })
        }, function () {
            var e, t = se.createElement("input"), n = se.createElement("div"), r = se.createElement("select"),
                i = r.appendChild(se.createElement("option"));
            n = se.createElement("div"), n.setAttribute("className", "t"), n.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", e = n.getElementsByTagName("a")[0], t.setAttribute("type", "checkbox"), n.appendChild(t), e = n.getElementsByTagName("a")[0], e.style.cssText = "top:1px", me.getSetAttribute = "t" !== n.className, me.style = /top/.test(e.getAttribute("style")), me.hrefNormalized = "/a" === e.getAttribute("href"), me.checkOn = !!t.value, me.optSelected = i.selected, me.enctype = !!se.createElement("form").enctype, r.disabled = !0, me.optDisabled = !i.disabled, t = se.createElement("input"), t.setAttribute("value", ""), me.input = "" === t.getAttribute("value"), t.value = "t", t.setAttribute("type", "radio"), me.radioValue = "t" === t.value
        }();
        var _t = /\r/g, Ht = /[\x20\t\r\n\f]+/g;
        ge.fn.extend({
            val: function (e) {
                var t, n, r, i = this[0];
                {
                    if (arguments.length)return r = ge.isFunction(e), this.each(function (n) {
                        var i;
                        1 === this.nodeType && (i = r ? e.call(this, n, ge(this).val()) : e, null == i ? i = "" : "number" == typeof i ? i += "" : ge.isArray(i) && (i = ge.map(i, function (e) {
                                return null == e ? "" : e + ""
                            })), t = ge.valHooks[this.type] || ge.valHooks[this.nodeName.toLowerCase()], t && "set" in t && void 0 !== t.set(this, i, "value") || (this.value = i))
                    });
                    if (i)return t = ge.valHooks[i.type] || ge.valHooks[i.nodeName.toLowerCase()], t && "get" in t && void 0 !== (n = t.get(i, "value")) ? n : (n = i.value, "string" == typeof n ? n.replace(_t, "") : null == n ? "" : n)
                }
            }
        }), ge.extend({
            valHooks: {
                option: {
                    get: function (e) {
                        var t = ge.find.attr(e, "value");
                        return null != t ? t : ge.trim(ge.text(e)).replace(Ht, " ")
                    }
                }, select: {
                    get: function (e) {
                        for (var t, n, r = e.options, i = e.selectedIndex, o = "select-one" === e.type || i < 0, a = o ? null : [], s = o ? i + 1 : r.length, l = i < 0 ? s : o ? i : 0; l < s; l++)if (n = r[l], (n.selected || l === i) && (me.optDisabled ? !n.disabled : null === n.getAttribute("disabled")) && (!n.parentNode.disabled || !ge.nodeName(n.parentNode, "optgroup"))) {
                            if (t = ge(n).val(), o)return t;
                            a.push(t)
                        }
                        return a
                    }, set: function (e, t) {
                        for (var n, r, i = e.options, o = ge.makeArray(t), a = i.length; a--;)if (r = i[a], ge.inArray(ge.valHooks.option.get(r), o) > -1)try {
                            r.selected = n = !0
                        } catch (e) {
                            r.scrollHeight
                        } else r.selected = !1;
                        return n || (e.selectedIndex = -1), i
                    }
                }
            }
        }), ge.each(["radio", "checkbox"], function () {
            ge.valHooks[this] = {
                set: function (e, t) {
                    if (ge.isArray(t))return e.checked = ge.inArray(ge(e).val(), t) > -1
                }
            }, me.checkOn || (ge.valHooks[this].get = function (e) {
                return null === e.getAttribute("value") ? "on" : e.value
            })
        });
        var qt, Mt, Ot = ge.expr.attrHandle, It = /^(?:checked|selected)$/i, Ft = me.getSetAttribute, Bt = me.input;
        ge.fn.extend({
            attr: function (e, t) {
                return ze(this, ge.attr, e, t, arguments.length > 1)
            }, removeAttr: function (e) {
                return this.each(function () {
                    ge.removeAttr(this, e)
                })
            }
        }), ge.extend({
            attr: function (e, t, n) {
                var r, i, o = e.nodeType;
                if (3 !== o && 8 !== o && 2 !== o)return "undefined" == typeof e.getAttribute ? ge.prop(e, t, n) : (1 === o && ge.isXMLDoc(e) || (t = t.toLowerCase(), i = ge.attrHooks[t] || (ge.expr.match.bool.test(t) ? Mt : qt)), void 0 !== n ? null === n ? void ge.removeAttr(e, t) : i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r : (e.setAttribute(t, n + ""), n) : i && "get" in i && null !== (r = i.get(e, t)) ? r : (r = ge.find.attr(e, t), null == r ? void 0 : r))
            }, attrHooks: {
                type: {
                    set: function (e, t) {
                        if (!me.radioValue && "radio" === t && ge.nodeName(e, "input")) {
                            var n = e.value;
                            return e.setAttribute("type", t), n && (e.value = n), t
                        }
                    }
                }
            }, removeAttr: function (e, t) {
                var n, r, i = 0, o = t && t.match(_e);
                if (o && 1 === e.nodeType)for (; n = o[i++];)r = ge.propFix[n] || n, ge.expr.match.bool.test(n) ? Bt && Ft || !It.test(n) ? e[r] = !1 : e[ge.camelCase("default-" + n)] = e[r] = !1 : ge.attr(e, n, ""), e.removeAttribute(Ft ? n : r)
            }
        }), Mt = {
            set: function (e, t, n) {
                return t === !1 ? ge.removeAttr(e, n) : Bt && Ft || !It.test(n) ? e.setAttribute(!Ft && ge.propFix[n] || n, n) : e[ge.camelCase("default-" + n)] = e[n] = !0, n
            }
        }, ge.each(ge.expr.match.bool.source.match(/\w+/g), function (e, t) {
            var n = Ot[t] || ge.find.attr;
            Bt && Ft || !It.test(t) ? Ot[t] = function (e, t, r) {
                var i, o;
                return r || (o = Ot[t], Ot[t] = i, i = null != n(e, t, r) ? t.toLowerCase() : null, Ot[t] = o), i
            } : Ot[t] = function (e, t, n) {
                if (!n)return e[ge.camelCase("default-" + t)] ? t.toLowerCase() : null
            }
        }), Bt && Ft || (ge.attrHooks.value = {
            set: function (e, t, n) {
                return ge.nodeName(e, "input") ? void(e.defaultValue = t) : qt && qt.set(e, t, n)
            }
        }), Ft || (qt = {
            set: function (e, t, n) {
                var r = e.getAttributeNode(n);
                if (r || e.setAttributeNode(r = e.ownerDocument.createAttribute(n)), r.value = t += "", "value" === n || t === e.getAttribute(n))return t
            }
        }, Ot.id = Ot.name = Ot.coords = function (e, t, n) {
            var r;
            if (!n)return (r = e.getAttributeNode(t)) && "" !== r.value ? r.value : null
        }, ge.valHooks.button = {
            get: function (e, t) {
                var n = e.getAttributeNode(t);
                if (n && n.specified)return n.value
            }, set: qt.set
        }, ge.attrHooks.contenteditable = {
            set: function (e, t, n) {
                qt.set(e, "" !== t && t, n)
            }
        }, ge.each(["width", "height"], function (e, t) {
            ge.attrHooks[t] = {
                set: function (e, n) {
                    if ("" === n)return e.setAttribute(t, "auto"), n
                }
            }
        })), me.style || (ge.attrHooks.style = {
            get: function (e) {
                return e.style.cssText || void 0
            }, set: function (e, t) {
                return e.style.cssText = t + ""
            }
        });
        var Rt = /^(?:input|select|textarea|button|object)$/i, Wt = /^(?:a|area)$/i;
        ge.fn.extend({
            prop: function (e, t) {
                return ze(this, ge.prop, e, t, arguments.length > 1)
            }, removeProp: function (e) {
                return e = ge.propFix[e] || e, this.each(function () {
                    try {
                        this[e] = void 0, delete this[e]
                    } catch (e) {
                    }
                })
            }
        }), ge.extend({
            prop: function (e, t, n) {
                var r, i, o = e.nodeType;
                if (3 !== o && 8 !== o && 2 !== o)return 1 === o && ge.isXMLDoc(e) || (t = ge.propFix[t] || t, i = ge.propHooks[t]), void 0 !== n ? i && "set" in i && void 0 !== (r = i.set(e, n, t)) ? r : e[t] = n : i && "get" in i && null !== (r = i.get(e, t)) ? r : e[t]
            }, propHooks: {
                tabIndex: {
                    get: function (e) {
                        var t = ge.find.attr(e, "tabindex");
                        return t ? parseInt(t, 10) : Rt.test(e.nodeName) || Wt.test(e.nodeName) && e.href ? 0 : -1
                    }
                }
            }, propFix: {for: "htmlFor", class: "className"}
        }), me.hrefNormalized || ge.each(["href", "src"], function (e, t) {
            ge.propHooks[t] = {
                get: function (e) {
                    return e.getAttribute(t, 4)
                }
            }
        }), me.optSelected || (ge.propHooks.selected = {
            get: function (e) {
                var t = e.parentNode;
                return t && (t.selectedIndex, t.parentNode && t.parentNode.selectedIndex), null
            }, set: function (e) {
                var t = e.parentNode;
                t && (t.selectedIndex, t.parentNode && t.parentNode.selectedIndex)
            }
        }), ge.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"], function () {
            ge.propFix[this.toLowerCase()] = this
        }), me.enctype || (ge.propFix.enctype = "encoding");
        var zt = /[\t\r\n\f]/g;
        ge.fn.extend({
            addClass: function (e) {
                var t, n, r, i, o, a, s, l = 0;
                if (ge.isFunction(e))return this.each(function (t) {
                    ge(this).addClass(e.call(this, t, U(this)))
                });
                if ("string" == typeof e && e)for (t = e.match(_e) || []; n = this[l++];)if (i = U(n), r = 1 === n.nodeType && (" " + i + " ").replace(zt, " ")) {
                    for (a = 0; o = t[a++];)r.indexOf(" " + o + " ") < 0 && (r += o + " ");
                    s = ge.trim(r), i !== s && ge.attr(n, "class", s)
                }
                return this
            }, removeClass: function (e) {
                var t, n, r, i, o, a, s, l = 0;
                if (ge.isFunction(e))return this.each(function (t) {
                    ge(this).removeClass(e.call(this, t, U(this)))
                });
                if (!arguments.length)return this.attr("class", "");
                if ("string" == typeof e && e)for (t = e.match(_e) || []; n = this[l++];)if (i = U(n), r = 1 === n.nodeType && (" " + i + " ").replace(zt, " ")) {
                    for (a = 0; o = t[a++];)for (; r.indexOf(" " + o + " ") > -1;)r = r.replace(" " + o + " ", " ");
                    s = ge.trim(r), i !== s && ge.attr(n, "class", s)
                }
                return this
            }, toggleClass: function (e, t) {
                var n = typeof e;
                return "boolean" == typeof t && "string" === n ? t ? this.addClass(e) : this.removeClass(e) : ge.isFunction(e) ? this.each(function (n) {
                    ge(this).toggleClass(e.call(this, n, U(this), t), t)
                }) : this.each(function () {
                    var t, r, i, o;
                    if ("string" === n)for (r = 0, i = ge(this), o = e.match(_e) || []; t = o[r++];)i.hasClass(t) ? i.removeClass(t) : i.addClass(t); else void 0 !== e && "boolean" !== n || (t = U(this), t && ge._data(this, "__className__", t), ge.attr(this, "class", t || e === !1 ? "" : ge._data(this, "__className__") || ""));
                })
            }, hasClass: function (e) {
                var t, n, r = 0;
                for (t = " " + e + " "; n = this[r++];)if (1 === n.nodeType && (" " + U(n) + " ").replace(zt, " ").indexOf(t) > -1)return !0;
                return !1
            }
        }), ge.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "), function (e, t) {
            ge.fn[t] = function (e, n) {
                return arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
            }
        }), ge.fn.extend({
            hover: function (e, t) {
                return this.mouseenter(e).mouseleave(t || e)
            }
        });
        var $t = n.location, Xt = ge.now(), Vt = /\?/,
            Gt = /(,)|(\[|{)|(}|])|"(?:[^"\\\r\n]|\\["\\\/bfnrt]|\\u[\da-fA-F]{4})*"\s*:?|true|false|null|-?(?!0\d)\d+(?:\.\d+|)(?:[eE][+-]?\d+|)/g;
        ge.parseJSON = function (e) {
            if (n.JSON && n.JSON.parse)return n.JSON.parse(e + "");
            var t, r = null, i = ge.trim(e + "");
            return i && !ge.trim(i.replace(Gt, function (e, n, i, o) {
                return t && n && (r = 0), 0 === r ? e : (t = i || n, r += !o - !i, "")
            })) ? Function("return " + i)() : ge.error("Invalid JSON: " + e)
        }, ge.parseXML = function (e) {
            var t, r;
            if (!e || "string" != typeof e)return null;
            try {
                n.DOMParser ? (r = new n.DOMParser, t = r.parseFromString(e, "text/xml")) : (t = new n.ActiveXObject("Microsoft.XMLDOM"), t.async = "false", t.loadXML(e))
            } catch (e) {
                t = void 0
            }
            return t && t.documentElement && !t.getElementsByTagName("parsererror").length || ge.error("Invalid XML: " + e), t
        };
        var Ut = /#.*$/, Qt = /([?&])_=[^&]*/, Jt = /^(.*?):[ \t]*([^\r\n]*)\r?$/gm,
            Yt = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/, Zt = /^(?:GET|HEAD)$/, Kt = /^\/\//,
            en = /^([\w.+-]+:)(?:\/\/(?:[^\/?#]*@|)([^\/?#:]*)(?::(\d+)|)|)/, tn = {}, nn = {}, rn = "*/".concat("*"),
            on = $t.href, an = en.exec(on.toLowerCase()) || [];
        ge.extend({
            active: 0,
            lastModified: {},
            etag: {},
            ajaxSettings: {
                url: on,
                type: "GET",
                isLocal: Yt.test(an[1]),
                global: !0,
                processData: !0,
                async: !0,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                accepts: {
                    "*": rn,
                    text: "text/plain",
                    html: "text/html",
                    xml: "application/xml, text/xml",
                    json: "application/json, text/javascript"
                },
                contents: {xml: /\bxml\b/, html: /\bhtml/, json: /\bjson\b/},
                responseFields: {xml: "responseXML", text: "responseText", json: "responseJSON"},
                converters: {"* text": String, "text html": !0, "text json": ge.parseJSON, "text xml": ge.parseXML},
                flatOptions: {url: !0, context: !0}
            },
            ajaxSetup: function (e, t) {
                return t ? Y(Y(e, ge.ajaxSettings), t) : Y(ge.ajaxSettings, e)
            },
            ajaxPrefilter: Q(tn),
            ajaxTransport: Q(nn),
            ajax: function (e, t) {
                function r(e, t, r, i) {
                    var o, d, y, b, w, T = t;
                    2 !== x && (x = 2, l && n.clearTimeout(l), c = void 0, s = i || "", C.readyState = e > 0 ? 4 : 0, o = e >= 200 && e < 300 || 304 === e, r && (b = Z(p, C, r)), b = K(p, b, C, o), o ? (p.ifModified && (w = C.getResponseHeader("Last-Modified"), w && (ge.lastModified[a] = w), w = C.getResponseHeader("etag"), w && (ge.etag[a] = w)), 204 === e || "HEAD" === p.type ? T = "nocontent" : 304 === e ? T = "notmodified" : (T = b.state, d = b.data, y = b.error, o = !y)) : (y = T, !e && T || (T = "error", e < 0 && (e = 0))), C.status = e, C.statusText = (t || T) + "", o ? m.resolveWith(f, [d, T, C]) : m.rejectWith(f, [C, T, y]), C.statusCode(g), g = void 0, u && h.trigger(o ? "ajaxSuccess" : "ajaxError", [C, p, o ? d : y]), v.fireWith(f, [C, T]), u && (h.trigger("ajaxComplete", [C, p]), --ge.active || ge.event.trigger("ajaxStop")))
                }

                "object" == typeof e && (t = e, e = void 0), t = t || {};
                var i, o, a, s, l, u, c, d, p = ge.ajaxSetup({}, t), f = p.context || p,
                    h = p.context && (f.nodeType || f.jquery) ? ge(f) : ge.event, m = ge.Deferred(),
                    v = ge.Callbacks("once memory"), g = p.statusCode || {}, y = {}, b = {}, x = 0, w = "canceled",
                    C = {
                        readyState: 0, getResponseHeader: function (e) {
                            var t;
                            if (2 === x) {
                                if (!d)for (d = {}; t = Jt.exec(s);)d[t[1].toLowerCase()] = t[2];
                                t = d[e.toLowerCase()]
                            }
                            return null == t ? null : t
                        }, getAllResponseHeaders: function () {
                            return 2 === x ? s : null
                        }, setRequestHeader: function (e, t) {
                            var n = e.toLowerCase();
                            return x || (e = b[n] = b[n] || e, y[e] = t), this
                        }, overrideMimeType: function (e) {
                            return x || (p.mimeType = e), this
                        }, statusCode: function (e) {
                            var t;
                            if (e)if (x < 2)for (t in e)g[t] = [g[t], e[t]]; else C.always(e[C.status]);
                            return this
                        }, abort: function (e) {
                            var t = e || w;
                            return c && c.abort(t), r(0, t), this
                        }
                    };
                if (m.promise(C).complete = v.add, C.success = C.done, C.error = C.fail, p.url = ((e || p.url || on) + "").replace(Ut, "").replace(Kt, an[1] + "//"), p.type = t.method || t.type || p.method || p.type, p.dataTypes = ge.trim(p.dataType || "*").toLowerCase().match(_e) || [""], null == p.crossDomain && (i = en.exec(p.url.toLowerCase()), p.crossDomain = !(!i || i[1] === an[1] && i[2] === an[2] && (i[3] || ("http:" === i[1] ? "80" : "443")) === (an[3] || ("http:" === an[1] ? "80" : "443")))), p.data && p.processData && "string" != typeof p.data && (p.data = ge.param(p.data, p.traditional)), J(tn, p, t, C), 2 === x)return C;
                u = ge.event && p.global, u && 0 === ge.active++ && ge.event.trigger("ajaxStart"), p.type = p.type.toUpperCase(), p.hasContent = !Zt.test(p.type), a = p.url, p.hasContent || (p.data && (a = p.url += (Vt.test(a) ? "&" : "?") + p.data, delete p.data), p.cache === !1 && (p.url = Qt.test(a) ? a.replace(Qt, "$1_=" + Xt++) : a + (Vt.test(a) ? "&" : "?") + "_=" + Xt++)), p.ifModified && (ge.lastModified[a] && C.setRequestHeader("If-Modified-Since", ge.lastModified[a]), ge.etag[a] && C.setRequestHeader("If-None-Match", ge.etag[a])), (p.data && p.hasContent && p.contentType !== !1 || t.contentType) && C.setRequestHeader("Content-Type", p.contentType), C.setRequestHeader("Accept", p.dataTypes[0] && p.accepts[p.dataTypes[0]] ? p.accepts[p.dataTypes[0]] + ("*" !== p.dataTypes[0] ? ", " + rn + "; q=0.01" : "") : p.accepts["*"]);
                for (o in p.headers)C.setRequestHeader(o, p.headers[o]);
                if (p.beforeSend && (p.beforeSend.call(f, C, p) === !1 || 2 === x))return C.abort();
                w = "abort";
                for (o in{success: 1, error: 1, complete: 1})C[o](p[o]);
                if (c = J(nn, p, t, C)) {
                    if (C.readyState = 1, u && h.trigger("ajaxSend", [C, p]), 2 === x)return C;
                    p.async && p.timeout > 0 && (l = n.setTimeout(function () {
                        C.abort("timeout")
                    }, p.timeout));
                    try {
                        x = 1, c.send(y, r)
                    } catch (e) {
                        if (!(x < 2))throw e;
                        r(-1, e)
                    }
                } else r(-1, "No Transport");
                return C
            },
            getJSON: function (e, t, n) {
                return ge.get(e, t, n, "json")
            },
            getScript: function (e, t) {
                return ge.get(e, void 0, t, "script")
            }
        }), ge.each(["get", "post"], function (e, t) {
            ge[t] = function (e, n, r, i) {
                return ge.isFunction(n) && (i = i || r, r = n, n = void 0), ge.ajax(ge.extend({
                    url: e,
                    type: t,
                    dataType: i,
                    data: n,
                    success: r
                }, ge.isPlainObject(e) && e))
            }
        }), ge._evalUrl = function (e) {
            return ge.ajax({url: e, type: "GET", dataType: "script", cache: !0, async: !1, global: !1, throws: !0})
        }, ge.fn.extend({
            wrapAll: function (e) {
                if (ge.isFunction(e))return this.each(function (t) {
                    ge(this).wrapAll(e.call(this, t))
                });
                if (this[0]) {
                    var t = ge(e, this[0].ownerDocument).eq(0).clone(!0);
                    this[0].parentNode && t.insertBefore(this[0]), t.map(function () {
                        for (var e = this; e.firstChild && 1 === e.firstChild.nodeType;)e = e.firstChild;
                        return e
                    }).append(this)
                }
                return this
            }, wrapInner: function (e) {
                return ge.isFunction(e) ? this.each(function (t) {
                    ge(this).wrapInner(e.call(this, t))
                }) : this.each(function () {
                    var t = ge(this), n = t.contents();
                    n.length ? n.wrapAll(e) : t.append(e)
                })
            }, wrap: function (e) {
                var t = ge.isFunction(e);
                return this.each(function (n) {
                    ge(this).wrapAll(t ? e.call(this, n) : e)
                })
            }, unwrap: function () {
                return this.parent().each(function () {
                    ge.nodeName(this, "body") || ge(this).replaceWith(this.childNodes)
                }).end()
            }
        }), ge.expr.filters.hidden = function (e) {
            return me.reliableHiddenOffsets() ? e.offsetWidth <= 0 && e.offsetHeight <= 0 && !e.getClientRects().length : te(e)
        }, ge.expr.filters.visible = function (e) {
            return !ge.expr.filters.hidden(e)
        };
        var sn = /%20/g, ln = /\[\]$/, un = /\r?\n/g, cn = /^(?:submit|button|image|reset|file)$/i,
            dn = /^(?:input|select|textarea|keygen)/i;
        ge.param = function (e, t) {
            var n, r = [], i = function (e, t) {
                t = ge.isFunction(t) ? t() : null == t ? "" : t, r[r.length] = encodeURIComponent(e) + "=" + encodeURIComponent(t)
            };
            if (void 0 === t && (t = ge.ajaxSettings && ge.ajaxSettings.traditional), ge.isArray(e) || e.jquery && !ge.isPlainObject(e)) ge.each(e, function () {
                i(this.name, this.value)
            }); else for (n in e)ne(n, e[n], t, i);
            return r.join("&").replace(sn, "+")
        }, ge.fn.extend({
            serialize: function () {
                return ge.param(this.serializeArray())
            }, serializeArray: function () {
                return this.map(function () {
                    var e = ge.prop(this, "elements");
                    return e ? ge.makeArray(e) : this
                }).filter(function () {
                    var e = this.type;
                    return this.name && !ge(this).is(":disabled") && dn.test(this.nodeName) && !cn.test(e) && (this.checked || !$e.test(e))
                }).map(function (e, t) {
                    var n = ge(this).val();
                    return null == n ? null : ge.isArray(n) ? ge.map(n, function (e) {
                        return {name: t.name, value: e.replace(un, "\r\n")}
                    }) : {name: t.name, value: n.replace(un, "\r\n")}
                }).get()
            }
        }), ge.ajaxSettings.xhr = void 0 !== n.ActiveXObject ? function () {
            return this.isLocal ? ie() : se.documentMode > 8 ? re() : /^(get|post|head|put|delete|options)$/i.test(this.type) && re() || ie()
        } : re;
        var pn = 0, fn = {}, hn = ge.ajaxSettings.xhr();
        n.attachEvent && n.attachEvent("onunload", function () {
            for (var e in fn)fn[e](void 0, !0)
        }), me.cors = !!hn && "withCredentials" in hn, hn = me.ajax = !!hn, hn && ge.ajaxTransport(function (e) {
            if (!e.crossDomain || me.cors) {
                var t;
                return {
                    send: function (r, i) {
                        var o, a = e.xhr(), s = ++pn;
                        if (a.open(e.type, e.url, e.async, e.username, e.password), e.xhrFields)for (o in e.xhrFields)a[o] = e.xhrFields[o];
                        e.mimeType && a.overrideMimeType && a.overrideMimeType(e.mimeType), e.crossDomain || r["X-Requested-With"] || (r["X-Requested-With"] = "XMLHttpRequest");
                        for (o in r)void 0 !== r[o] && a.setRequestHeader(o, r[o] + "");
                        a.send(e.hasContent && e.data || null), t = function (n, r) {
                            var o, l, u;
                            if (t && (r || 4 === a.readyState))if (delete fn[s], t = void 0, a.onreadystatechange = ge.noop, r) 4 !== a.readyState && a.abort(); else {
                                u = {}, o = a.status, "string" == typeof a.responseText && (u.text = a.responseText);
                                try {
                                    l = a.statusText
                                } catch (e) {
                                    l = ""
                                }
                                o || !e.isLocal || e.crossDomain ? 1223 === o && (o = 204) : o = u.text ? 200 : 404
                            }
                            u && i(o, l, u, a.getAllResponseHeaders())
                        }, e.async ? 4 === a.readyState ? n.setTimeout(t) : a.onreadystatechange = fn[s] = t : t()
                    }, abort: function () {
                        t && t(void 0, !0)
                    }
                }
            }
        }), ge.ajaxSetup({
            accepts: {script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"},
            contents: {script: /\b(?:java|ecma)script\b/},
            converters: {
                "text script": function (e) {
                    return ge.globalEval(e), e
                }
            }
        }), ge.ajaxPrefilter("script", function (e) {
            void 0 === e.cache && (e.cache = !1), e.crossDomain && (e.type = "GET", e.global = !1)
        }), ge.ajaxTransport("script", function (e) {
            if (e.crossDomain) {
                var t, n = se.head || ge("head")[0] || se.documentElement;
                return {
                    send: function (r, i) {
                        t = se.createElement("script"), t.async = !0, e.scriptCharset && (t.charset = e.scriptCharset), t.src = e.url, t.onload = t.onreadystatechange = function (e, n) {
                            (n || !t.readyState || /loaded|complete/.test(t.readyState)) && (t.onload = t.onreadystatechange = null, t.parentNode && t.parentNode.removeChild(t), t = null, n || i(200, "success"))
                        }, n.insertBefore(t, n.firstChild)
                    }, abort: function () {
                        t && t.onload(void 0, !0)
                    }
                }
            }
        });
        var mn = [], vn = /(=)\?(?=&|$)|\?\?/;
        ge.ajaxSetup({
            jsonp: "callback", jsonpCallback: function () {
                var e = mn.pop() || ge.expando + "_" + Xt++;
                return this[e] = !0, e
            }
        }), ge.ajaxPrefilter("json jsonp", function (e, t, r) {
            var i, o, a,
                s = e.jsonp !== !1 && (vn.test(e.url) ? "url" : "string" == typeof e.data && 0 === (e.contentType || "").indexOf("application/x-www-form-urlencoded") && vn.test(e.data) && "data");
            if (s || "jsonp" === e.dataTypes[0])return i = e.jsonpCallback = ge.isFunction(e.jsonpCallback) ? e.jsonpCallback() : e.jsonpCallback, s ? e[s] = e[s].replace(vn, "$1" + i) : e.jsonp !== !1 && (e.url += (Vt.test(e.url) ? "&" : "?") + e.jsonp + "=" + i), e.converters["script json"] = function () {
                return a || ge.error(i + " was not called"), a[0]
            }, e.dataTypes[0] = "json", o = n[i], n[i] = function () {
                a = arguments
            }, r.always(function () {
                void 0 === o ? ge(n).removeProp(i) : n[i] = o, e[i] && (e.jsonpCallback = t.jsonpCallback, mn.push(i)), a && ge.isFunction(o) && o(a[0]), a = o = void 0
            }), "script"
        }), ge.parseHTML = function (e, t, n) {
            if (!e || "string" != typeof e)return null;
            "boolean" == typeof t && (n = t, t = !1), t = t || se;
            var r = Ne.exec(e), i = !n && [];
            return r ? [t.createElement(r[1])] : (r = w([e], t, i), i && i.length && ge(i).remove(), ge.merge([], r.childNodes))
        };
        var gn = ge.fn.load;
        ge.fn.load = function (e, t, n) {
            if ("string" != typeof e && gn)return gn.apply(this, arguments);
            var r, i, o, a = this, s = e.indexOf(" ");
            return s > -1 && (r = ge.trim(e.slice(s, e.length)), e = e.slice(0, s)), ge.isFunction(t) ? (n = t, t = void 0) : t && "object" == typeof t && (i = "POST"), a.length > 0 && ge.ajax({
                url: e,
                type: i || "GET",
                dataType: "html",
                data: t
            }).done(function (e) {
                o = arguments, a.html(r ? ge("<div>").append(ge.parseHTML(e)).find(r) : e)
            }).always(n && function (e, t) {
                    a.each(function () {
                        n.apply(this, o || [e.responseText, t, e])
                    })
                }), this
        }, ge.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"], function (e, t) {
            ge.fn[t] = function (e) {
                return this.on(t, e)
            }
        }), ge.expr.filters.animated = function (e) {
            return ge.grep(ge.timers, function (t) {
                return e === t.elem
            }).length
        }, ge.offset = {
            setOffset: function (e, t, n) {
                var r, i, o, a, s, l, u, c = ge.css(e, "position"), d = ge(e), p = {};
                "static" === c && (e.style.position = "relative"), s = d.offset(), o = ge.css(e, "top"), l = ge.css(e, "left"), u = ("absolute" === c || "fixed" === c) && ge.inArray("auto", [o, l]) > -1, u ? (r = d.position(), a = r.top, i = r.left) : (a = parseFloat(o) || 0, i = parseFloat(l) || 0), ge.isFunction(t) && (t = t.call(e, n, ge.extend({}, s))), null != t.top && (p.top = t.top - s.top + a), null != t.left && (p.left = t.left - s.left + i), "using" in t ? t.using.call(e, p) : d.css(p)
            }
        }, ge.fn.extend({
            offset: function (e) {
                if (arguments.length)return void 0 === e ? this : this.each(function (t) {
                    ge.offset.setOffset(this, e, t)
                });
                var t, n, r = {top: 0, left: 0}, i = this[0], o = i && i.ownerDocument;
                if (o)return t = o.documentElement, ge.contains(t, i) ? ("undefined" != typeof i.getBoundingClientRect && (r = i.getBoundingClientRect()), n = oe(o), {
                    top: r.top + (n.pageYOffset || t.scrollTop) - (t.clientTop || 0),
                    left: r.left + (n.pageXOffset || t.scrollLeft) - (t.clientLeft || 0)
                }) : r
            }, position: function () {
                if (this[0]) {
                    var e, t, n = {top: 0, left: 0}, r = this[0];
                    return "fixed" === ge.css(r, "position") ? t = r.getBoundingClientRect() : (e = this.offsetParent(), t = this.offset(), ge.nodeName(e[0], "html") || (n = e.offset()), n.top += ge.css(e[0], "borderTopWidth", !0), n.left += ge.css(e[0], "borderLeftWidth", !0)), {
                        top: t.top - n.top - ge.css(r, "marginTop", !0),
                        left: t.left - n.left - ge.css(r, "marginLeft", !0)
                    }
                }
            }, offsetParent: function () {
                return this.map(function () {
                    for (var e = this.offsetParent; e && !ge.nodeName(e, "html") && "static" === ge.css(e, "position");)e = e.offsetParent;
                    return e || gt
                })
            }
        }), ge.each({scrollLeft: "pageXOffset", scrollTop: "pageYOffset"}, function (e, t) {
            var n = /Y/.test(t);
            ge.fn[e] = function (r) {
                return ze(this, function (e, r, i) {
                    var o = oe(e);
                    return void 0 === i ? o ? t in o ? o[t] : o.document.documentElement[r] : e[r] : void(o ? o.scrollTo(n ? ge(o).scrollLeft() : i, n ? i : ge(o).scrollTop()) : e[r] = i)
                }, e, r, arguments.length, null)
            }
        }), ge.each(["top", "left"], function (e, t) {
            ge.cssHooks[t] = q(me.pixelPosition, function (e, n) {
                if (n)return n = bt(e, t), mt.test(n) ? ge(e).position()[t] + "px" : n
            })
        }), ge.each({Height: "height", Width: "width"}, function (e, t) {
            ge.each({padding: "inner" + e, content: t, "": "outer" + e}, function (n, r) {
                ge.fn[r] = function (r, i) {
                    var o = arguments.length && (n || "boolean" != typeof r),
                        a = n || (r === !0 || i === !0 ? "margin" : "border");
                    return ze(this, function (t, n, r) {
                        var i;
                        return ge.isWindow(t) ? t.document.documentElement["client" + e] : 9 === t.nodeType ? (i = t.documentElement, Math.max(t.body["scroll" + e], i["scroll" + e], t.body["offset" + e], i["offset" + e], i["client" + e])) : void 0 === r ? ge.css(t, n, a) : ge.style(t, n, r, a)
                    }, t, o ? r : void 0, o, null)
                }
            })
        }), ge.fn.extend({
            bind: function (e, t, n) {
                return this.on(e, null, t, n)
            }, unbind: function (e, t) {
                return this.off(e, null, t)
            }, delegate: function (e, t, n, r) {
                return this.on(t, e, n, r)
            }, undelegate: function (e, t, n) {
                return 1 === arguments.length ? this.off(e, "**") : this.off(t, e || "**", n)
            }
        }), ge.fn.size = function () {
            return this.length
        }, ge.fn.andSelf = ge.fn.addBack, r = [], i = function () {
            return ge
        }.apply(t, r), !(void 0 !== i && (e.exports = i));
        var yn = n.jQuery, bn = n.$;
        return ge.noConflict = function (e) {
            return n.$ === ge && (n.$ = bn), e && n.jQuery === ge && (n.jQuery = yn), ge
        }, o || (n.jQuery = n.$ = ge), ge
    })
}, function (e, t) {
}, , , , , , , function (e, t, n) {
    (function (t) {
        "use strict";
        var r = (n(10), n(30));
        e.exports = function () {
            var e = {domain: app.api}, n = function (e, n, i, o, a) {
                var s = this;
                t.ajax({
                    url: e, type: n, data: i, dataType: "json", success: function (e) {
                        var t = parseInt(e.responseCode);
                        1e3 === t ? o(e, !0) : 1004 === t ? window.location.href = "login.html?" + location.href : a ? o(e, !0) : s.tipsAlert(null, e.errorMsg, "tips-panel", "info", null, null, r)
                    }, error: function (e) {
                        s.tipsAlert(null, "系统异常，请刷新重试!", "tips-panel", "fail", null, null, r)
                    }
                })
            }, i = function (e, n, r, i, o, a, s) {
                var l = {title: e || "温馨提示", tipType: i || "info", html: n, btns: o}, u = function () {
                    var e = s(l), n = ".util-tips-box";
                    t(n).length > 0 && t(n).remove(), t(e).find(".close").on("click", function () {
                        t(this).parents(n).remove(), a && "tips-warning-alert" != r && a()
                    }).end().find(".util-submit").on("click", function () {
                        t(this).parents(n).remove(), a()
                    }).end().find(".util-cencer").on("click", function () {
                        t(this).parents(n).remove()
                    }).end().appendTo("body")
                };
                u()
            }, o = function (e) {
                var t = new RegExp("(^|&)" + e + "=([^&]*)(&|$)", "i"), n = window.location.search.substr(1).match(t);
                return null != n ? decodeURIComponent(n[2]) : null
            };
            return {config: e, ajaxHelp: n, tipsAlert: i, getQueryString: o}
        }()
    }).call(t, n(1))
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o, a, s = null != t ? t : e.nullContext || {}, l = n.helperMissing, u = "function";
            return '  <div class="alert-box tips-success util-tips-box" >\n    <div class="over-hide"></div>\n    <div class="alert-Box-content message-box-content">\n      <div class="title">' + e.escapeExpression((a = null != (a = n.title || (null != t ? t.title : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "title",
                    hash: {},
                    data: i
                }) : a)) + '</div>\n      <div class="content">' + (null != (a = null != (a = n.btns || (null != t ? t.btns : t)) ? a : l, o = typeof a === u ? a.call(s, {
                    name: "btns",
                    hash: {},
                    data: i
                }) : a) ? o : "") + '</div>\n      <div class="spirit close"></div>\n    </div>\n  </div>\n'
        }, useData: !0
    })
}, function (e, t, n) {
    e.exports = n(12).default
}, function (e, t, n) {
    "use strict";
    function r(e) {
        return e && e.__esModule ? e : {default: e}
    }

    function i(e) {
        if (e && e.__esModule)return e;
        var t = {};
        if (null != e)for (var n in e)Object.prototype.hasOwnProperty.call(e, n) && (t[n] = e[n]);
        return t.default = e, t
    }

    function o() {
        var e = new s.HandlebarsEnvironment;
        return f.extend(e, s), e.SafeString = u.default, e.Exception = d.default, e.Utils = f, e.escapeExpression = f.escapeExpression, e.VM = m, e.template = function (t) {
            return m.template(t, e)
        }, e
    }

    t.__esModule = !0;
    var a = n(13), s = i(a), l = n(27), u = r(l), c = n(15), d = r(c), p = n(14), f = i(p), h = n(28), m = i(h),
        v = n(29), g = r(v), y = o();
    y.create = o, g.default(y), y.default = y, t.default = y, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    function r(e) {
        return e && e.__esModule ? e : {default: e}
    }

    function i(e, t, n) {
        this.helpers = e || {}, this.partials = t || {}, this.decorators = n || {}, l.registerDefaultHelpers(this), u.registerDefaultDecorators(this)
    }

    t.__esModule = !0, t.HandlebarsEnvironment = i;
    var o = n(14), a = n(15), s = r(a), l = n(16), u = n(24), c = n(26), d = r(c), p = "4.0.11";
    t.VERSION = p;
    var f = 7;
    t.COMPILER_REVISION = f;
    var h = {
        1: "<= 1.0.rc.2",
        2: "== 1.0.0-rc.3",
        3: "== 1.0.0-rc.4",
        4: "== 1.x.x",
        5: "== 2.0.0-alpha.x",
        6: ">= 2.0.0-beta.1",
        7: ">= 4.0.0"
    };
    t.REVISION_CHANGES = h;
    var m = "[object Object]";
    i.prototype = {
        constructor: i, logger: d.default, log: d.default.log, registerHelper: function (e, t) {
            if (o.toString.call(e) === m) {
                if (t)throw new s.default("Arg not supported with multiple helpers");
                o.extend(this.helpers, e)
            } else this.helpers[e] = t
        }, unregisterHelper: function (e) {
            delete this.helpers[e]
        }, registerPartial: function (e, t) {
            if (o.toString.call(e) === m) o.extend(this.partials, e); else {
                if ("undefined" == typeof t)throw new s.default('Attempting to register a partial called "' + e + '" as undefined');
                this.partials[e] = t
            }
        }, unregisterPartial: function (e) {
            delete this.partials[e]
        }, registerDecorator: function (e, t) {
            if (o.toString.call(e) === m) {
                if (t)throw new s.default("Arg not supported with multiple decorators");
                o.extend(this.decorators, e)
            } else this.decorators[e] = t
        }, unregisterDecorator: function (e) {
            delete this.decorators[e]
        }
    };
    var v = d.default.log;
    t.log = v, t.createFrame = o.createFrame, t.logger = d.default
}, function (e, t) {
    "use strict";
    function n(e) {
        return c[e]
    }

    function r(e) {
        for (var t = 1; t < arguments.length; t++)for (var n in arguments[t])Object.prototype.hasOwnProperty.call(arguments[t], n) && (e[n] = arguments[t][n]);
        return e
    }

    function i(e, t) {
        for (var n = 0, r = e.length; n < r; n++)if (e[n] === t)return n;
        return -1
    }

    function o(e) {
        if ("string" != typeof e) {
            if (e && e.toHTML)return e.toHTML();
            if (null == e)return "";
            if (!e)return e + "";
            e = "" + e
        }
        return p.test(e) ? e.replace(d, n) : e
    }

    function a(e) {
        return !e && 0 !== e || !(!m(e) || 0 !== e.length)
    }

    function s(e) {
        var t = r({}, e);
        return t._parent = e, t
    }

    function l(e, t) {
        return e.path = t, e
    }

    function u(e, t) {
        return (e ? e + "." : "") + t
    }

    t.__esModule = !0, t.extend = r, t.indexOf = i, t.escapeExpression = o, t.isEmpty = a, t.createFrame = s, t.blockParams = l, t.appendContextPath = u;
    var c = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#x27;", "`": "&#x60;", "=": "&#x3D;"},
        d = /[&<>"'`=]/g, p = /[&<>"'`=]/, f = Object.prototype.toString;
    t.toString = f;
    var h = function (e) {
        return "function" == typeof e
    };
    h(/x/) && (t.isFunction = h = function (e) {
        return "function" == typeof e && "[object Function]" === f.call(e)
    }), t.isFunction = h;
    var m = Array.isArray || function (e) {
            return !(!e || "object" != typeof e) && "[object Array]" === f.call(e)
        };
    t.isArray = m
}, function (e, t) {
    "use strict";
    function n(e, t) {
        var i = t && t.loc, o = void 0, a = void 0;
        i && (o = i.start.line, a = i.start.column, e += " - " + o + ":" + a);
        for (var s = Error.prototype.constructor.call(this, e), l = 0; l < r.length; l++)this[r[l]] = s[r[l]];
        Error.captureStackTrace && Error.captureStackTrace(this, n);
        try {
            i && (this.lineNumber = o, Object.defineProperty ? Object.defineProperty(this, "column", {
                value: a,
                enumerable: !0
            }) : this.column = a)
        } catch (e) {
        }
    }

    t.__esModule = !0;
    var r = ["description", "fileName", "lineNumber", "message", "name", "number", "stack"];
    n.prototype = new Error, t.default = n, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    function r(e) {
        return e && e.__esModule ? e : {default: e}
    }

    function i(e) {
        a.default(e), l.default(e), c.default(e), p.default(e), h.default(e), v.default(e), y.default(e)
    }

    t.__esModule = !0, t.registerDefaultHelpers = i;
    var o = n(17), a = r(o), s = n(18), l = r(s), u = n(19), c = r(u), d = n(20), p = r(d), f = n(21), h = r(f),
        m = n(22), v = r(m), g = n(23), y = r(g)
}, function (e, t, n) {
    "use strict";
    t.__esModule = !0;
    var r = n(14);
    t.default = function (e) {
        e.registerHelper("blockHelperMissing", function (t, n) {
            var i = n.inverse, o = n.fn;
            if (t === !0)return o(this);
            if (t === !1 || null == t)return i(this);
            if (r.isArray(t))return t.length > 0 ? (n.ids && (n.ids = [n.name]), e.helpers.each(t, n)) : i(this);
            if (n.data && n.ids) {
                var a = r.createFrame(n.data);
                a.contextPath = r.appendContextPath(n.data.contextPath, n.name), n = {data: a}
            }
            return o(t, n)
        })
    }, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    function r(e) {
        return e && e.__esModule ? e : {default: e}
    }

    t.__esModule = !0;
    var i = n(14), o = n(15), a = r(o);
    t.default = function (e) {
        e.registerHelper("each", function (e, t) {
            function n(t, n, o) {
                u && (u.key = t, u.index = n, u.first = 0 === n, u.last = !!o, c && (u.contextPath = c + t)), l += r(e[t], {
                    data: u,
                    blockParams: i.blockParams([e[t], t], [c + t, null])
                })
            }

            if (!t)throw new a.default("Must pass iterator to #each");
            var r = t.fn, o = t.inverse, s = 0, l = "", u = void 0, c = void 0;
            if (t.data && t.ids && (c = i.appendContextPath(t.data.contextPath, t.ids[0]) + "."), i.isFunction(e) && (e = e.call(this)), t.data && (u = i.createFrame(t.data)), e && "object" == typeof e)if (i.isArray(e))for (var d = e.length; s < d; s++)s in e && n(s, s, s === e.length - 1); else {
                var p = void 0;
                for (var f in e)e.hasOwnProperty(f) && (void 0 !== p && n(p, s - 1), p = f, s++);
                void 0 !== p && n(p, s - 1, !0)
            }
            return 0 === s && (l = o(this)), l
        })
    }, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    function r(e) {
        return e && e.__esModule ? e : {default: e}
    }

    t.__esModule = !0;
    var i = n(15), o = r(i);
    t.default = function (e) {
        e.registerHelper("helperMissing", function () {
            if (1 !== arguments.length)throw new o.default('Missing helper: "' + arguments[arguments.length - 1].name + '"')
        })
    }, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    t.__esModule = !0;
    var r = n(14);
    t.default = function (e) {
        e.registerHelper("if", function (e, t) {
            return r.isFunction(e) && (e = e.call(this)), !t.hash.includeZero && !e || r.isEmpty(e) ? t.inverse(this) : t.fn(this)
        }), e.registerHelper("unless", function (t, n) {
            return e.helpers.if.call(this, t, {fn: n.inverse, inverse: n.fn, hash: n.hash})
        })
    }, e.exports = t.default
}, function (e, t) {
    "use strict";
    t.__esModule = !0, t.default = function (e) {
        e.registerHelper("log", function () {
            for (var t = [void 0], n = arguments[arguments.length - 1], r = 0; r < arguments.length - 1; r++)t.push(arguments[r]);
            var i = 1;
            null != n.hash.level ? i = n.hash.level : n.data && null != n.data.level && (i = n.data.level), t[0] = i, e.log.apply(e, t)
        })
    }, e.exports = t.default
}, function (e, t) {
    "use strict";
    t.__esModule = !0, t.default = function (e) {
        e.registerHelper("lookup", function (e, t) {
            return e && e[t]
        })
    }, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    t.__esModule = !0;
    var r = n(14);
    t.default = function (e) {
        e.registerHelper("with", function (e, t) {
            r.isFunction(e) && (e = e.call(this));
            var n = t.fn;
            if (r.isEmpty(e))return t.inverse(this);
            var i = t.data;
            return t.data && t.ids && (i = r.createFrame(t.data), i.contextPath = r.appendContextPath(t.data.contextPath, t.ids[0])), n(e, {
                data: i,
                blockParams: r.blockParams([e], [i && i.contextPath])
            })
        })
    }, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    function r(e) {
        return e && e.__esModule ? e : {default: e}
    }

    function i(e) {
        a.default(e)
    }

    t.__esModule = !0, t.registerDefaultDecorators = i;
    var o = n(25), a = r(o)
}, function (e, t, n) {
    "use strict";
    t.__esModule = !0;
    var r = n(14);
    t.default = function (e) {
        e.registerDecorator("inline", function (e, t, n, i) {
            var o = e;
            return t.partials || (t.partials = {}, o = function (i, o) {
                var a = n.partials;
                n.partials = r.extend({}, a, t.partials);
                var s = e(i, o);
                return n.partials = a, s
            }), t.partials[i.args[0]] = i.fn, o
        })
    }, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    t.__esModule = !0;
    var r = n(14), i = {
        methodMap: ["debug", "info", "warn", "error"], level: "info", lookupLevel: function (e) {
            if ("string" == typeof e) {
                var t = r.indexOf(i.methodMap, e.toLowerCase());
                e = t >= 0 ? t : parseInt(e, 10)
            }
            return e
        }, log: function (e) {
            if (e = i.lookupLevel(e), "undefined" != typeof console && i.lookupLevel(i.level) <= e) {
                var t = i.methodMap[e];
                console[t] || (t = "log");
                for (var n = arguments.length, r = Array(n > 1 ? n - 1 : 0), o = 1; o < n; o++)r[o - 1] = arguments[o];
                console[t].apply(console, r)
            }
        }
    };
    t.default = i, e.exports = t.default
}, function (e, t) {
    "use strict";
    function n(e) {
        this.string = e
    }

    t.__esModule = !0, n.prototype.toString = n.prototype.toHTML = function () {
        return "" + this.string
    }, t.default = n, e.exports = t.default
}, function (e, t, n) {
    "use strict";
    function r(e) {
        return e && e.__esModule ? e : {default: e}
    }

    function i(e) {
        if (e && e.__esModule)return e;
        var t = {};
        if (null != e)for (var n in e)Object.prototype.hasOwnProperty.call(e, n) && (t[n] = e[n]);
        return t.default = e, t
    }

    function o(e) {
        var t = e && e[0] || 1, n = g.COMPILER_REVISION;
        if (t !== n) {
            if (t < n) {
                var r = g.REVISION_CHANGES[n], i = g.REVISION_CHANGES[t];
                throw new v.default("Template was precompiled with an older version of Handlebars than the current runtime. Please update your precompiler to a newer version (" + r + ") or downgrade your runtime to an older version (" + i + ").")
            }
            throw new v.default("Template was precompiled with a newer version of Handlebars than the current runtime. Please update your runtime to a newer version (" + e[1] + ").")
        }
    }

    function a(e, t) {
        function n(n, r, i) {
            i.hash && (r = h.extend({}, r, i.hash), i.ids && (i.ids[0] = !0)), n = t.VM.resolvePartial.call(this, n, r, i);
            var o = t.VM.invokePartial.call(this, n, r, i);
            if (null == o && t.compile && (i.partials[i.name] = t.compile(n, e.compilerOptions, t), o = i.partials[i.name](r, i)), null != o) {
                if (i.indent) {
                    for (var a = o.split("\n"), s = 0, l = a.length; s < l && (a[s] || s + 1 !== l); s++)a[s] = i.indent + a[s];
                    o = a.join("\n")
                }
                return o
            }
            throw new v.default("The partial " + i.name + " could not be compiled when running in runtime-only mode")
        }

        function r(t) {
            function n(t) {
                return "" + e.main(i, t, i.helpers, i.partials, a, l, s)
            }

            var o = arguments.length <= 1 || void 0 === arguments[1] ? {} : arguments[1], a = o.data;
            r._setup(o), !o.partial && e.useData && (a = d(t, a));
            var s = void 0, l = e.useBlockParams ? [] : void 0;
            return e.useDepths && (s = o.depths ? t != o.depths[0] ? [t].concat(o.depths) : o.depths : [t]), (n = p(e.main, n, i, o.depths || [], a, l))(t, o)
        }

        if (!t)throw new v.default("No environment passed to template");
        if (!e || !e.main)throw new v.default("Unknown template object: " + typeof e);
        e.main.decorator = e.main_d, t.VM.checkRevision(e.compiler);
        var i = {
            strict: function (e, t) {
                if (!(t in e))throw new v.default('"' + t + '" not defined in ' + e);
                return e[t]
            }, lookup: function (e, t) {
                for (var n = e.length, r = 0; r < n; r++)if (e[r] && null != e[r][t])return e[r][t]
            }, lambda: function (e, t) {
                return "function" == typeof e ? e.call(t) : e
            }, escapeExpression: h.escapeExpression, invokePartial: n, fn: function (t) {
                var n = e[t];
                return n.decorator = e[t + "_d"], n
            }, programs: [], program: function (e, t, n, r, i) {
                var o = this.programs[e], a = this.fn(e);
                return t || i || r || n ? o = s(this, e, a, t, n, r, i) : o || (o = this.programs[e] = s(this, e, a)), o
            }, data: function (e, t) {
                for (; e && t--;)e = e._parent;
                return e
            }, merge: function (e, t) {
                var n = e || t;
                return e && t && e !== t && (n = h.extend({}, t, e)), n
            }, nullContext: Object.seal({}), noop: t.VM.noop, compilerInfo: e.compiler
        };
        return r.isTop = !0, r._setup = function (n) {
            n.partial ? (i.helpers = n.helpers, i.partials = n.partials, i.decorators = n.decorators) : (i.helpers = i.merge(n.helpers, t.helpers), e.usePartial && (i.partials = i.merge(n.partials, t.partials)), (e.usePartial || e.useDecorators) && (i.decorators = i.merge(n.decorators, t.decorators)))
        }, r._child = function (t, n, r, o) {
            if (e.useBlockParams && !r)throw new v.default("must pass block params");
            if (e.useDepths && !o)throw new v.default("must pass parent depths");
            return s(i, t, e[t], n, 0, r, o)
        }, r
    }

    function s(e, t, n, r, i, o, a) {
        function s(t) {
            var i = arguments.length <= 1 || void 0 === arguments[1] ? {} : arguments[1], s = a;
            return !a || t == a[0] || t === e.nullContext && null === a[0] || (s = [t].concat(a)), n(e, t, e.helpers, e.partials, i.data || r, o && [i.blockParams].concat(o), s)
        }

        return s = p(n, s, e, a, r, o), s.program = t, s.depth = a ? a.length : 0, s.blockParams = i || 0, s
    }

    function l(e, t, n) {
        return e ? e.call || n.name || (n.name = e, e = n.partials[e]) : e = "@partial-block" === n.name ? n.data["partial-block"] : n.partials[n.name], e
    }

    function u(e, t, n) {
        var r = n.data && n.data["partial-block"];
        n.partial = !0, n.ids && (n.data.contextPath = n.ids[0] || n.data.contextPath);
        var i = void 0;
        if (n.fn && n.fn !== c && !function () {
                n.data = g.createFrame(n.data);
                var e = n.fn;
                i = n.data["partial-block"] = function (t) {
                    var n = arguments.length <= 1 || void 0 === arguments[1] ? {} : arguments[1];
                    return n.data = g.createFrame(n.data), n.data["partial-block"] = r, e(t, n)
                }, e.partials && (n.partials = h.extend({}, n.partials, e.partials))
            }(), void 0 === e && i && (e = i), void 0 === e)throw new v.default("The partial " + n.name + " could not be found");
        if (e instanceof Function)return e(t, n)
    }

    function c() {
        return ""
    }

    function d(e, t) {
        return t && "root" in t || (t = t ? g.createFrame(t) : {}, t.root = e), t
    }

    function p(e, t, n, r, i, o) {
        if (e.decorator) {
            var a = {};
            t = e.decorator(t, a, n, r && r[0], i, o, r), h.extend(t, a)
        }
        return t
    }

    t.__esModule = !0, t.checkRevision = o, t.template = a, t.wrapProgram = s, t.resolvePartial = l, t.invokePartial = u, t.noop = c;
    var f = n(14), h = i(f), m = n(15), v = r(m), g = n(13)
}, function (e, t) {
    (function (n) {
        "use strict";
        t.__esModule = !0, t.default = function (e) {
            var t = "undefined" != typeof n ? n : window, r = t.Handlebars;
            e.noConflict = function () {
                return t.Handlebars === e && (t.Handlebars = r), e
            }
        }, e.exports = t.default
    }).call(t, function () {
        return this
    }())
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o, a, s = null != t ? t : e.nullContext || {}, l = n.helperMissing, u = "function",
                c = e.escapeExpression;
            return '  <div class="alert-box tips-success util-tips-box" >\n    <div class="over-hide"></div>\n    <div class="alert-Box-content">\n      <div class="title">' + c((a = null != (a = n.title || (null != t ? t.title : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "title",
                    hash: {},
                    data: i
                }) : a)) + '</div>\n      <div class="tips">\n        <i class="spirit ' + c((a = null != (a = n.tipType || (null != t ? t.tipType : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "tipType",
                    hash: {},
                    data: i
                }) : a)) + '"></i>\n        <sapn class="tips-text">' + c((a = null != (a = n.html || (null != t ? t.html : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "html",
                    hash: {},
                    data: i
                }) : a)) + '</span>\n      </div>\n      <div class="btns">' + (null != (a = null != (a = n.btns || (null != t ? t.btns : t)) ? a : l, o = typeof a === u ? a.call(s, {
                    name: "btns",
                    hash: {},
                    data: i
                }) : a) ? o : "") + '</div>\n      <div class="spirit close"></div>\n    </div>\n  </div>\n'
        }, useData: !0
    })
}, , function (e, t) {
}, , function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        1: function (e, t, n, r, i) {
            var o, a = null != t ? t : e.nullContext || {}, s = n.helperMissing, l = "function", u = e.escapeExpression;
            return '      <li>\n        <label class="game-btn">\n          <a data-app-code="' + u((o = null != (o = n.appCode || (null != t ? t.appCode : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "appCode",
                    hash: {},
                    data: i
                }) : o)) + '" \n              data-app-name="' + u((o = null != (o = n.gameName || (null != t ? t.gameName : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "gameName",
                    hash: {},
                    data: i
                }) : o)) + '" \n              data-charge-point="' + u((o = null != (o = n.chargePoint || (null != t ? t.chargePoint : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "chargePoint",
                    hash: {},
                    data: i
                }) : o)) + '" \n              data-unit="' + u((o = null != (o = n.unit || (null != t ? t.unit : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "unit",
                    hash: {},
                    data: i
                }) : o)) + '"\n              >\n              ' + u((o = null != (o = n.gameName || (null != t ? t.gameName : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "gameName",
                    hash: {},
                    data: i
                }) : o)) + ' <i class="spirit"></i></a>\n        </label>\n      </li>\n'
        }, compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o;
            return '<ul class="clearfix">\n' + (null != (o = n.each.call(null != t ? t : e.nullContext || {}, null != t ? t.gameList : t, {
                    name: "each", hash: {}, fn: e.program(1, i, 0), inverse: e.noop, data: i
                })) ? o : "") + "  </ul>"
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o;
            return '<div class="pay-manal">\n  <span>如果支付页面无法打开，</span><a href="' + e.escapeExpression((o = null != (o = n.href || (null != t ? t.href : t)) ? o : n.helperMissing, "function" == typeof o ? o.call(null != t ? t : e.nullContext || {}, {
                    name: "href",
                    hash: {},
                    data: i
                }) : o)) + '" target="_blank">支付请点击这里</a>\n</div>\n<div class="pay-info">\n  <a id="already-pay-btn" class="btn btn-yes" href="javascript:void(0);">已支付</a>\n  <a id="pay-close-btn" class="btn btn-no" href="javascript:void(0);">支付遇到问题</a>\n</div>\n'
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            return '<div class="pay-info">\n  <a id="pay-close-btn" class="btn btn-no" href="javascript:void(0);">关闭</a>\n</div>\n'
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            return '<div class="pay-info">\n  <a id="pay-close-btn" class="btn btn-yes" href="javascript:void(0);">关闭</a>\n</div>\n'
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        1: function (e, t, n, r, i) {
            var o = e.lambda, a = e.escapeExpression;
            return '  <li><label class="price-btn"><a href="javascript:void(0);" data-value="' + a(o(t, t)) + '">' + a(o(t, t)) + ' <i class="spirit"></i></a></label></li>\n'
        }, compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o;
            return (null != (o = n.each.call(null != t ? t : e.nullContext || {}, null != t ? t.amountList : t, {
                    name: "each",
                    hash: {},
                    fn: e.program(1, i, 0),
                    inverse: e.noop,
                    data: i
                })) ? o : "") + '<li class="price-other">\n  <input type="number" class="price-other-value" name="otherPrice" placeholder="其他" value="" min="10" max="100000"/> \n  <span>元</span> \n</li>'
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        1: function (e, t, n, r, i) {
            var o, a = null != t ? t : e.nullContext || {}, s = n.helperMissing, l = "function", u = e.escapeExpression;
            return '    <li>\n      <label class="area-btn">\n        <a data-zone-id="' + u((o = null != (o = n.zoneId || (null != t ? t.zoneId : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "zoneId",
                    hash: {},
                    data: i
                }) : o)) + '"\n            >\n            ' + u((o = null != (o = n.areaServerName || (null != t ? t.areaServerName : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "areaServerName",
                    hash: {},
                    data: i
                }) : o)) + ' <i class="spirit"></i></a>\n      </label>\n    </li>\n'
        }, compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o;
            return '<ul class="clearfix">\n' + (null != (o = n.each.call(null != t ? t : e.nullContext || {}, null != t ? t.areaList : t, {
                    name: "each",
                    hash: {},
                    fn: e.program(1, i, 0),
                    inverse: e.noop,
                    data: i
                })) ? o : "") + "</ul>"
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        1: function (e, t, n, r, i) {
            return 'target="_blank"'
        }, 3: function (e, t, n, r, i) {
            var o, a = null != t ? t : e.nullContext || {}, s = n.helperMissing, l = "function", u = e.escapeExpression;
            return '  <input type="text" name="' + u((o = null != (o = n.name || (null != t ? t.name : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "name",
                    hash: {},
                    data: i
                }) : o)) + '" value="' + u((o = null != (o = n.value || (null != t ? t.value : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "value",
                    hash: {},
                    data: i
                }) : o)) + '" />\n'
        }, compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o, a = null != t ? t : e.nullContext || {};
            return '<form id="pay-submit" action="https://mapi.alipay.com/gateway.do" method="GET" ' + (null != (o = n.unless.call(a, null != t ? t.isSelf : t, {
                    name: "unless",
                    hash: {},
                    fn: e.program(1, i, 0),
                    inverse: e.noop,
                    data: i
                })) ? o : "") + ">\n" + (null != (o = n.each.call(a, null != t ? t.kvs : t, {
                    name: "each",
                    hash: {},
                    fn: e.program(3, i, 0),
                    inverse: e.noop,
                    data: i
                })) ? o : "") + '\n<input type="submit" value="提交" />\n</form>\n'
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        1: function (e, t, n, r, i) {
            var o, a, s = null != t ? t : e.nullContext || {}, l = n.helperMissing, u = "function",
                c = e.escapeExpression;
            return '    <div class="bq-panel">\n      <label>\n        <input type="checkbox" data-bq="' + c((a = null != (a = n.bqAmount || (null != t ? t.bqAmount : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "bqAmount",
                    hash: {},
                    data: i
                }) : a)) + '" data-scale="' + c(e.lambda(null != (o = null != t ? t.currency : t) ? o.scale : o, t)) + '" name="useBqFlag"/> \n        <span>使用宝券余额:</span> ' + c((a = null != (a = n.bqAmount || (null != t ? t.bqAmount : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "bqAmount",
                    hash: {},
                    data: i
                }) : a)) + '</label>\n      <span class="tip" style="display: none;">您的宝券数量不足</span>\n    </div>\n'
        }, compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o, a, s = null != t ? t : e.nullContext || {}, l = n.helperMissing, u = "function",
                c = e.escapeExpression;
            return '<form id="pay-qbao-form">\n  <div class="info">订单支付成功，请确认支付</div>\n  <div class="prop-price"><span>充值金额:</span> <em>' + c((a = null != (a = n.propPrice || (null != t ? t.propPrice : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "propPrice",
                    hash: {},
                    data: i
                }) : a)) + '</em> <i> 元</i></div>\n  <div class="pay"><span>支付方式:</span> <i>' + c((a = null != (a = n.pay || (null != t ? t.pay : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "pay",
                    hash: {},
                    data: i
                }) : a)) + '</i></div>\n  <div class="qb-amount"><span>可用金额:</span> <i>' + c((a = null != (a = n.qbAmountDesc || (null != t ? t.qbAmountDesc : t)) ? a : l, typeof a === u ? a.call(s, {
                    name: "qbAmountDesc",
                    hash: {},
                    data: i
                }) : a)) + " 元</i></div>\n" + (null != (o = n.if.call(s, null != t ? t.isBqFlag : t, {
                    name: "if",
                    hash: {},
                    fn: e.program(1, i, 0),
                    inverse: e.noop,
                    data: i
                })) ? o : "") + '  <div class="pay-pwd">\n    <input type="password" name="transPassWord" placeholder="请输入交易密码" />\n    <span class="tip" style="display: none;"></span>\n  </div>\n  <div class="btns">\n    <input type="submit" class="btn btn-ok" value="支付" />\n  </div>\n</form>\n'
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o, a = null != t ? t : e.nullContext || {}, s = n.helperMissing, l = "function", u = e.escapeExpression;
            return "<p>（" + u((o = null != (o = n.rmb || (null != t ? t.rmb : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "rmb",
                    hash: {},
                    data: i
                }) : o)) + " 元人民币兑换" + u((o = null != (o = n.gameName || (null != t ? t.gameName : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "gameName",
                    hash: {},
                    data: i
                }) : o)) + " " + u((o = null != (o = n.points || (null != t ? t.points : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "points",
                    hash: {},
                    data: i
                }) : o)) + u((o = null != (o = n.unit || (null != t ? t.unit : t)) ? o : s, typeof o === l ? o.call(a, {
                    name: "unit",
                    hash: {},
                    data: i
                }) : o)) + "）</p>"
        }, useData: !0
    })
}, function (e, t, n) {
    var r = n(11);
    e.exports = (r.default || r).template({
        compiler: [7, ">= 4.0.0"], main: function (e, t, n, r, i) {
            var o;
            return '<header><span>我的账号：</span></header>\n<div class="user-info-name">' + e.escapeExpression((o = null != (o = n.username || (null != t ? t.username : t)) ? o : n.helperMissing, "function" == typeof o ? o.call(null != t ? t : e.nullContext || {}, {
                    name: "username",
                    hash: {},
                    data: i
                }) : o)) + '</div>\n<div class="line"></div>\n'
        }, useData: !0
    })
}]);