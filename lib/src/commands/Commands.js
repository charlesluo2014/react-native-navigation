import _ from 'lodash';

export default class Commands {
  constructor(nativeCommandsSender, layoutTreeParser, layoutTreeCrawler) {
    this.nativeCommandsSender = nativeCommandsSender;
    this.layoutTreeParser = layoutTreeParser;
    this.layoutTreeCrawler = layoutTreeCrawler;
  }

  setRoot(simpleApi) {
    const input = _.cloneDeep(simpleApi);
    const layout = this.layoutTreeParser.parseFromSimpleJSON(input);
    this.layoutTreeCrawler.crawl(layout);
    return this.nativeCommandsSender.setRoot(layout);
  }

  showModal(simpleApi) {
    const input = _.cloneDeep(simpleApi);
    const layout = this.layoutTreeParser.parseFromSimpleJSON(input);
    this.layoutTreeCrawler.crawl(layout);
    return this.nativeCommandsSender.showModal(layout);
  }

  dismissModal(id) {
    return this.nativeCommandsSender.dismissModal(id);
  }

  dismissAllModals() {
    return this.nativeCommandsSender.dismissAllModals();
  }

  push(onContainerId, containerData) {
    const input = _.cloneDeep(containerData);
    const layout = this.layoutTreeParser.createContainer(input);
    this.layoutTreeCrawler.crawl(layout);
    return this.nativeCommandsSender.push(onContainerId, layout);
  }

  pop(containerId) {
    return this.nativeCommandsSender.pop(containerId);
  }

  popTo(containerId) {
    return this.nativeCommandsSender.popTo(containerId);
  }

  popToRoot(containerId) {
    return this.nativeCommandsSender.popToRoot(containerId);
  }
}

