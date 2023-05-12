class Script {

  process(eventStr) {
    const event = JSON.parse(eventStr);
    console.log(`Event name: ${event.name}`);
  }
}
